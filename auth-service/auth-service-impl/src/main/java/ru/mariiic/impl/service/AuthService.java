package ru.mariiic.impl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mariiic.api.request.*;
import ru.mariiic.api.response.TokenCoupleResponse;
import ru.mariiic.event.CustomerRegisteredEvent;
import ru.mariiic.event.OrganizerRegisteredEvent;
import ru.mariiic.event.StartEmailConfirmation;
import ru.mariiic.impl.dto.CustomerRegistrationInfo;
import ru.mariiic.impl.dto.OrganizerRegistrationInfo;
import ru.mariiic.impl.dto.UserRegistrationInfo;
import ru.mariiic.impl.exception.*;
import ru.mariiic.impl.model.OutboxNode;
import ru.mariiic.impl.model.UserAccount;
import ru.mariiic.impl.publisher.AuthEventPublisher;
import ru.mariiic.impl.repository.OutboxRepository;
import ru.mariiic.impl.repository.RefreshTokenRepository;
import ru.mariiic.impl.repository.RegistrationInfoRepository;
import ru.mariiic.impl.repository.UserRepository;
import ru.mariiic.impl.security.authorities.UserAuthority;
import ru.mariiic.impl.security.authorities.UserRole;
import ru.mariiic.impl.util.VerificationCodeGenerator;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${outbox.attempts}")
    private int attempts;

    private final JwtService jwtService;

    private final RefreshTokenRepository refreshTokenRepository;
    private final RegistrationInfoRepository registrationInfoRepository;
    private final UserRepository userRepository;
    private final OutboxRepository outboxRepository;

    private final PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper;

    private final AuthEventPublisher authEventPublisher;

    public TokenCoupleResponse refreshTokens(RefreshTokenRequest refreshTokenRequest) {
        UserAccount userAccount = refreshTokenRepository.findByTokenAndIsActive(refreshTokenRequest.refreshToken(), true).orElseThrow(() -> new RefreshTokenNotFoundException("no active token found")).getUser();
        return jwtService.generateTokenCouple(userAccount);
    }

    public TokenCoupleResponse login(UserLoginRequest request) {
        UserAccount user = userRepository.findByUsername(request.username()).orElseThrow(() -> new UsernameNotFoundException("username not found"));
        if (passwordEncoder.matches(request.password(), user.getPasswordHash())){
            return jwtService.generateTokenCouple(user);
        }
        throw new WrongPasswordException("wrong password");
    }

    public void logout(RefreshTokenRequest refreshTokenRequest) {
        UserAccount userAccount = refreshTokenRepository.findByTokenAndIsActive(refreshTokenRequest.refreshToken(), true).orElseThrow(() -> new RefreshTokenNotFoundException("no active token found")).getUser();
        jwtService.recallRefreshToken(userAccount);
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request, UUID userId) {
        UserAccount userAccount = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        if (passwordEncoder.matches(request.oldPassword(), userAccount.getPasswordHash())) {
            userAccount.changePassword(passwordEncoder.encode(request.newPassword()));
        }
        throw new WrongPasswordException("wrong password");
    }

    public void registerCustomer(CustomerRegistrationRequest request) {
        if (!existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException("username %s already exists".formatted(request.username()));
        }
        if (!existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("email %s already exists".formatted(request.email()));
        }
        String passwordHash = passwordEncoder.encode(request.password());
        String verificationCode = VerificationCodeGenerator.generateEmailVerificationCode();
        CustomerRegistrationInfo registrationInfo = CustomerRegistrationInfo.builder()
                .email(request.email())
                .username(request.username())
                .birthDate(request.birthDate())
                .passwordHash(passwordHash)
                .emailVerificationCode(verificationCode)
                .build();
        registrationInfoRepository.saveCustomer(registrationInfo);
        authEventPublisher.publishStartEmailConfirmationCommand(new StartEmailConfirmation(request.email(), verificationCode));
    }

    //todo модерация описания в OrganizerRegistrationRequest
    public void registerOrganizer(OrganizerRegistrationRequest request) {
        if (!existsByUsername(request.organizationName())) {
            throw new UsernameAlreadyExistsException("username %s already exists".formatted(request.organizationName()));
        }
        if (!existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("email %s already exists".formatted(request.email()));
        }
        String passwordHash = passwordEncoder.encode(request.password());
        String verificationCode = VerificationCodeGenerator.generateEmailVerificationCode();
        OrganizerRegistrationInfo registrationInfo = OrganizerRegistrationInfo.builder()
                .email(request.email())
                .passwordHash(passwordHash)
                .emailVerificationCode(verificationCode)
                .organizationName(request.organizationName())
                .description(request.description())
                .contactPerson(request.contact_person())
                .websiteUrl(request.websiteUrl())
                .build();
        registrationInfoRepository.saveOrganizer(registrationInfo);
        authEventPublisher.publishStartEmailConfirmationCommand(new StartEmailConfirmation(request.email(), verificationCode));
    }

    //todo разделить подтверждение на обновление почты и на создание профиля
    @Transactional
    public TokenCoupleResponse confirmEmail(EmailConfirmRequest confirmRequest) throws JsonProcessingException {
        Optional<Object> user = registrationInfoRepository.getUser(confirmRequest.email());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("email %s not found".formatted(confirmRequest.email()));
        }
        Object fromOptional = user.get();
        UserRegistrationInfo userRegistrationInfo = (UserRegistrationInfo) fromOptional;
        if (confirmRequest.emailConfirm() == null
                || !userRegistrationInfo.getEmailVerificationCode().equals(confirmRequest.emailConfirm())){
            throw new IncorrectEmailConfirmationException("email confirmation code mismatch");
        }
        UserAccount userAccount;
        if (fromOptional instanceof CustomerRegistrationInfo){
            CustomerRegistrationInfo registrationInfo = (CustomerRegistrationInfo) fromOptional;
            userAccount = new UserAccount(registrationInfo.getUsername(),
                    registrationInfo.getPasswordHash(),
                    registrationInfo.getEmail(),
                    UserRole.CUSTOMER,
                    null
            );
            userAccount = userRepository.save(userAccount);
            CustomerRegisteredEvent customerRegisteredEvent = new CustomerRegisteredEvent(
                    userAccount.getUserId(),
                    userAccount.getUsername(),
                    userAccount.getEmail(),
                    registrationInfo.getBirthDate()
            );
            outboxRepository.save(new OutboxNode(CustomerRegisteredEvent.class.getTypeName(),
                    objectMapper.writeValueAsString(customerRegisteredEvent),
                    attempts
                    ));
            return jwtService.generateTokenCouple(userAccount);
        } else if (fromOptional instanceof OrganizerRegistrationInfo){
            OrganizerRegistrationInfo registrationInfo = (OrganizerRegistrationInfo) fromOptional;
            userAccount = new UserAccount(registrationInfo.getOrganizationName(),
                    registrationInfo.getPasswordHash(),
                    registrationInfo.getEmail(),
                    UserRole.ORGANIZER,
                    Set.of(UserAuthority.CREATE_EVENTS, UserAuthority.DELETE_EVENTS, UserAuthority.MODIFY_EVENTS)
            );
            userAccount = userRepository.save(userAccount);
            OrganizerRegisteredEvent organizerRegisteredEvent = new OrganizerRegisteredEvent(
                    userAccount.getUserId(),
                    userAccount.getUsername(),
                    userAccount.getEmail(),
                    registrationInfo.getContactPerson(),
                    registrationInfo.getDescription(),
                    registrationInfo.getWebsiteUrl()
            );
            outboxRepository.save(new OutboxNode(OrganizerRegisteredEvent.class.getTypeName(),
                    objectMapper.writeValueAsString(organizerRegisteredEvent),
                    attempts
            ));
            return jwtService.generateTokenCouple(userAccount);
        }
        //todo исправить исключение
        throw new RuntimeException("internal error");
    }

    private boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
