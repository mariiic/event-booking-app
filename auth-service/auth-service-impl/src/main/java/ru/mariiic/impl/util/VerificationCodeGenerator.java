package ru.mariiic.impl.util;

import java.security.SecureRandom;

public class VerificationCodeGenerator {
    private static final SecureRandom random = new SecureRandom();

    public static String generateEmailVerificationCode() {
        return String.format("%06d", random.nextInt(1_000_000));
    }
}
