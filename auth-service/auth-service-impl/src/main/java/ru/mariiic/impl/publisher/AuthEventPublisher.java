package ru.mariiic.impl.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.stereotype.Component;
import ru.mariiic.event.CustomerRegisteredEvent;
import ru.mariiic.event.EmailChangedEvent;
import ru.mariiic.event.OrganizerRegisteredEvent;
import ru.mariiic.event.StartEmailConfirmation;
import ru.mariiic.util.RabbitNaming;

@RequiredArgsConstructor
@Component
public class AuthEventPublisher {
    private final AmqpTemplate amqpTemplate;

    public void publishStartEmailConfirmationCommand(StartEmailConfirmation event) {
        amqpTemplate.convertAndSend(RabbitNaming.EVENT_BOOKING_EXCHANGE,
                "auth.notification.email.confirmation",
                event,
                message -> {
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                }
        );
    }

    public void publishCustomerRegisteredEvent(CustomerRegisteredEvent event) {
        amqpTemplate.convertAndSend(RabbitNaming.EVENT_BOOKING_EXCHANGE,
                "auth.user.customer.registered",
                event,
                message -> {
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                }
        );
    }

    public void publishOrganizerRegisteredEvent(OrganizerRegisteredEvent event) {
        amqpTemplate.convertAndSend(RabbitNaming.EVENT_BOOKING_EXCHANGE,
                "auth.user.organizer.registered",
                event,
                message -> {
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                }
        );
    }

    public void publishEmailChangedEvent(EmailChangedEvent event) {
        amqpTemplate.convertAndSend(RabbitNaming.EVENT_BOOKING_EXCHANGE,
                "auth.user.email.changed",
                event,
                message -> {
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                }
        );
    }
}
