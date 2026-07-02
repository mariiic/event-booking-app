package ru.mariiic.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.mariiic.api.request.ChangePasswordRequest;
import ru.mariiic.api.request.RefreshTokenRequest;
import ru.mariiic.api.request.UserLoginRequest;
import ru.mariiic.api.response.TokenCoupleResponse;
import ru.mariiic.securitystarter.dto.UserInfo;

@RequestMapping("/api/auth")
public interface AuthApi {
    @PostMapping("/refresh")
    TokenCoupleResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest);

    @PostMapping("/login")
    TokenCoupleResponse login(@Valid @RequestBody UserLoginRequest loginRequest);

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest);

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void changePassword(@Valid ChangePasswordRequest changePasswordRequest, @AuthenticationPrincipal UserInfo userInfo);
}
