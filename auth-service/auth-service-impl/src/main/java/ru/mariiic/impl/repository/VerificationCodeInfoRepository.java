package ru.mariiic.impl.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mariiic.impl.dto.CustomerRegistrationInfo;
import ru.mariiic.impl.dto.OrganizerRegistrationInfo;
import ru.mariiic.impl.dto.UserEmailChangeInfo;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class VerificationCodeInfoRepository {
    @Value("${spring.redis.ttl}")
    private int ttl;

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveCustomer(CustomerRegistrationInfo info) {
        String email = info.getEmail();
        redisTemplate.opsForValue().set(email, info, ttl);
    }

    public void saveOrganizer(OrganizerRegistrationInfo info) {
        String email = info.getEmail();
        redisTemplate.opsForValue().set(email, info, ttl);
    }

    public void saveEmailChange(UserEmailChangeInfo info) {
        String key = String.valueOf(info.userId());
        redisTemplate.opsForValue().set(key, info, ttl);
    }

    @Transactional
    public Optional<Object> getUserByEmail(String email) {
        Optional<Object> found =  Optional.ofNullable(redisTemplate.opsForValue().get(email));
        if (found.isPresent()) {
            redisTemplate.delete(email);
        }
        return found;
    }

    @Transactional
    public Optional<Object> getUserById(UUID id) {
        Optional<Object> found =  Optional.ofNullable(redisTemplate.opsForValue().get(id));
        if (found.isPresent()) {
            redisTemplate.delete(String.valueOf(id));
        }
        return found;
    }
}
