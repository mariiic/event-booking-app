package ru.mariiic.impl.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mariiic.event.CustomerRegisteredEvent;
import ru.mariiic.event.EmailChangedEvent;
import ru.mariiic.event.OrganizerRegisteredEvent;
import ru.mariiic.event.StartEmailConfirmation;

@Component
@RequiredArgsConstructor
public class OutboxDispatcher {
    private final AuthEventPublisher authEventPublisher;
    private final ObjectMapper objectMapper;

    public boolean publishEventByTypeNaming(String eventTypeName, String eventPayload) throws JsonProcessingException {
        switch (eventTypeName) {
            case "OrganizerRegisteredEvent" ->{
                authEventPublisher.publishOrganizerRegisteredEvent(objectMapper.readValue(eventPayload, OrganizerRegisteredEvent.class));
                return true;
            }
            case "CustomerRegisteredEvent" -> {
                authEventPublisher.publishCustomerRegisteredEvent(objectMapper.readValue(eventPayload, CustomerRegisteredEvent.class));
                return true;
            }
            case "StartEmailConfirmationEvent" -> {
                authEventPublisher.publishStartEmailConfirmationCommand(objectMapper.readValue(eventPayload, StartEmailConfirmation.class));
                return true;
            }
            case "EmailChangedEvent" -> {
                authEventPublisher.publishEmailChangedEvent(objectMapper.readValue(eventPayload, EmailChangedEvent.class));
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}
