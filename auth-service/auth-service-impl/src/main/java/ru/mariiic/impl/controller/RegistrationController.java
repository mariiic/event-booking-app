package ru.mariiic.impl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.mariiic.api.RegistrationApi;
import ru.mariiic.api.request.CustomerRegistrationRequest;
import ru.mariiic.api.request.EmailConfirmRequest;
import ru.mariiic.api.request.OrganizerRegistrationRequest;
import ru.mariiic.api.response.TokenCoupleResponse;
import ru.mariiic.impl.service.AuthService;

@RestController
@RequiredArgsConstructor
public class RegistrationController implements RegistrationApi {
    private final AuthService authService;

    @Override
    public void registerCustomer(CustomerRegistrationRequest request) {
        authService.registerCustomer(request);
    }

    @Override
    public void registerOrganizer(OrganizerRegistrationRequest request) {
        authService.registerOrganizer(request);
    }

    @Override
    public TokenCoupleResponse confirmEmail(EmailConfirmRequest request) {
        return authService.confirmEmailOnRegistration(request);
    }
}
