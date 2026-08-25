package ru.mariiic.userserviceimpl.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.mariiic.event.CustomerRegisteredEvent;
import ru.mariiic.event.OrganizerRegisteredEvent;
import ru.mariiic.userserviceimpl.model.Customer;
import ru.mariiic.userserviceimpl.model.Organizer;
import ru.mariiic.userserviceimpl.service.CustomerService;
import ru.mariiic.userserviceimpl.service.OrganizerService;
import ru.mariiic.util.RabbitNaming;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserListener {
    private final CustomerService customerService;
    private final OrganizerService organizerService;

    @RabbitListener(queues = RabbitNaming.USER_SERVICE_QUEUE)
    public void handleCustomerRegistered(CustomerRegisteredEvent event) {
        log.info("Received CustomerRegisteredEvent {}", event);
        Customer customer = customerService.create(Customer.createNew(
                event.userId(),
                event.username(),
                event.email(),
                event.birthDate()));
        log.info("Created customer {}", customer);
    }

    @RabbitListener(queues = RabbitNaming.USER_SERVICE_QUEUE)
    public void handleOrganizerRegistered(OrganizerRegisteredEvent event) {
        log.info("Received OrganizerRegisteredEvent {}", event);
        Organizer organizer = organizerService.create(Organizer.createNew(
                event.userId(),
                event.organizationName(),
                event.email(),
                event.contact_person(),
                event.description(),
                event.websiteUrl()
        ));
        log.info("Created organizer {}", organizer);
    }
}
