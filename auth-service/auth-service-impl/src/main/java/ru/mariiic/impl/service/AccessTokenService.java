package ru.mariiic.impl.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mariiic.impl.model.UserAccount;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@Getter
public class AccessTokenService {
    @Value("${security.jwt.access.expiration.minutes}")
    private int accessExpirationInMinutes;

    @Value("${security.jwt.access.secret}")
    private String accessSecret;

    protected String generateAccessToken(UserAccount user) {
        String subject = user.getUserId().toString();
        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = Stream.concat(
                Stream.of("ROLE_" + user.getRole().name()),
                user.getAuthorities().stream().map(Enum::name)
        ).toList();
        claims.put("authorities", authorities);
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(Duration.ofMinutes(getAccessExpirationInMinutes()))))
                .signWith(getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(getAccessSecret().getBytes(StandardCharsets.UTF_8));
    }
}
