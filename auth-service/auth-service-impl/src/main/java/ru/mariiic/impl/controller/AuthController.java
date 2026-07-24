package ru.mariiic.impl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import ru.mariiic.api.AuthApi;
import ru.mariiic.api.request.*;
import ru.mariiic.api.response.TokenCoupleResponse;
import ru.mariiic.impl.service.AuthService;
import ru.mariiic.securitystarter.dto.UserInfo;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi{
    private final AuthService authService;

    @Override
    public TokenCoupleResponse refreshTokens(RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshTokens(refreshTokenRequest);
    }

    @Override
    public TokenCoupleResponse login(UserLoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Override
    public void logout(RefreshTokenRequest refreshTokenRequest) {
        authService.logout(refreshTokenRequest);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest, UserInfo userInfo) {
        authService.changePassword(changePasswordRequest, userInfo.getUserId());
    }

    @Override
    public void changeEmail(ChangeEmailRequest changeEmailRequest, UserInfo userInfo) {
        authService.changeEmail(changeEmailRequest, userInfo.getUserId());
    }

    @Override
    public void confirmEmail(EmailConfirmRequest request, @AuthenticationPrincipal UserInfo userInfo) {
        authService.confirmEmailOnChangeEmail(request, userInfo.getUserId());
    }
}
