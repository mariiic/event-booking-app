package ru.mariiic.api;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mariiic.api.request.CustomerRegistrationRequest;
import ru.mariiic.api.request.EmailConfirmRequest;
import ru.mariiic.api.request.OrganizerRegistrationRequest;
import ru.mariiic.api.response.TokenCoupleResponse;

@RequestMapping("/api/auth/register")
public interface RegistrationApi {
    @PostMapping("/customer")
    void registerCustomer(@Valid @RequestBody CustomerRegistrationRequest request);

    @PostMapping("/organizer")
    void registerOrganizer(@Valid @RequestBody OrganizerRegistrationRequest  request);

    @PostMapping("/confirm/email")
    TokenCoupleResponse confirmEmail(@Valid @RequestBody EmailConfirmRequest request);
}
