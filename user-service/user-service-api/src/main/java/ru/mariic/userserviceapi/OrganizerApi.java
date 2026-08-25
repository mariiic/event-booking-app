package ru.mariic.userserviceapi;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.mariic.userserviceapi.request.organizer.ContactPersonChangeRequest;
import ru.mariic.userserviceapi.request.organizer.WebsiteChangeRequest;
import ru.mariic.userserviceapi.response.OrganizerResponse;
import ru.mariiic.securitystarter.dto.UserInfo;

@RequestMapping("api/v1/organizer")
@PreAuthorize("hasRole('ORGANIZER')")
public interface OrganizerApi {
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    OrganizerResponse getProfile(@AuthenticationPrincipal UserInfo userInfo);

    @PatchMapping("/contactperson")
    @ResponseStatus(HttpStatus.OK)
    OrganizerResponse changeContactPerson(@AuthenticationPrincipal UserInfo userInfo, @Valid ContactPersonChangeRequest contactPersonChangeRequest);

    @PatchMapping("/websiteurl")
    @ResponseStatus(HttpStatus.OK)
    OrganizerResponse changeWebsiteUrl(@AuthenticationPrincipal UserInfo userInfo, @Valid WebsiteChangeRequest websiteChangeRequest);
}
