package ru.mariiic.impl.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mariiic.impl.dto.CustomerRegistrationInfo;
import ru.mariiic.impl.dto.OrganizerRegistrationInfo;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RegistrationInfoRepository {
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

    @Transactional
    public Optional<Object> getUser(String email) {
        Optional<Object> found =  Optional.ofNullable(redisTemplate.opsForValue().get(email));
        if (found.isPresent()) {
            redisTemplate.delete(email);
        }
        return found;
    }
}
