package ru.mariiic.impl.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class OutboxNode {
    private UUID id;
    private String messageType;
    private String messagePayload;
    private Instant createdAt;
    private int attempts;
    private OutboxStatus status;

    public OutboxNode(String messageType, String messagePayload, int attempts) {
        this.messageType = messageType;
        this.messagePayload = messagePayload;
        this.createdAt = Instant.now();
        this.attempts = attempts;
        this.status = OutboxStatus.NEW;
    }
}
