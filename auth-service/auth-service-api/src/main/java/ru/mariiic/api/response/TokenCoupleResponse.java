package ru.mariiic.api.response;

import lombok.Builder;

@Builder
public record TokenCoupleResponse(RefreshTokenResponse refreshToken,
                                  AccessTokenResponse accessToken) {
}
