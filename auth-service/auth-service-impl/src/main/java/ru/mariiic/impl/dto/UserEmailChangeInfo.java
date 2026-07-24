package ru.mariiic.impl.dto;


import java.util.UUID;

public record UserEmailChangeInfo(UUID userId,
                                  String newEmail,
                                  String verificationCode) {
}
