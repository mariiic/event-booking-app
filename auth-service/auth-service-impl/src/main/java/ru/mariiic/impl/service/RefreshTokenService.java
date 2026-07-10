package ru.mariiic.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mariiic.impl.model.RefreshToken;
import ru.mariiic.impl.model.UserAccount;
import ru.mariiic.impl.repository.RefreshTokenRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${security.jwt.refresh.expiration.days}")
    private int refreshExpirationInDays;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public String createRefreshToken(UserAccount userAccount) {
        markLatestAsInvalid(userAccount);
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken(token, userAccount, Instant.now().plus(Duration.ofDays(refreshExpirationInDays)));
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    private void markLatestAsInvalid(UserAccount userAccount) {
        List<RefreshToken> prev = refreshTokenRepository.findAllActiveByUserId(userAccount.getUserId());
        if (!prev.isEmpty()) {
            prev.forEach(p -> p.setIsActive(false));
        }
    }

    @Transactional
    public void recallRefreshToken(UserAccount userAccount) {
        markLatestAsInvalid(userAccount);
    }
}
