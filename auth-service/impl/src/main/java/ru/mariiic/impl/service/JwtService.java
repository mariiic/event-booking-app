package ru.mariiic.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mariiic.api.request.RefreshTokenRequest;
import ru.mariiic.api.response.AccessTokenResponse;
import ru.mariiic.api.response.RefreshTokenResponse;
import ru.mariiic.api.response.TokenCoupleResponse;
import ru.mariiic.impl.exception.RefreshTokenNotFoundException;
import ru.mariiic.impl.model.UserAccount;
import ru.mariiic.impl.repository.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

//    public TokenCoupleResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
//        UserAccount userAccount = refreshTokenRepository.findByTokenAndIsActive(refreshTokenRequest.refreshToken(), true).orElseThrow(() -> new RefreshTokenNotFoundException("no active token found")).getUser();
//        return generateTokenCouple(userAccount);
//    }



    public TokenCoupleResponse generateTokenCouple(UserAccount userAccount) {
        String refresh = refreshTokenService.createRefreshToken(userAccount);
        String access = accessTokenService.generateAccessToken(userAccount);
        return TokenCoupleResponse
                .builder()
                .accessToken(new AccessTokenResponse(access))
                .refreshToken(new RefreshTokenResponse(refresh))
                .build();
    }

    public void recallRefreshToken(UserAccount userAccount) {
        refreshTokenService.recallRefreshToken(userAccount);
    }
}
