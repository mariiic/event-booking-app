package ru.mariiic.userserviceimpl.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.mariic.userserviceapi.OrganizerApi;
import ru.mariic.userserviceapi.request.organizer.ContactPersonChangeRequest;
import ru.mariic.userserviceapi.request.organizer.WebsiteChangeRequest;
import ru.mariic.userserviceapi.response.OrganizerResponse;
import ru.mariiic.securitystarter.dto.UserInfo;
import ru.mariiic.userserviceimpl.service.OrganizerService;

@RestController
@RequiredArgsConstructor
public class OrganizerController implements OrganizerApi {
    private final OrganizerService organizerService;

    @Override
    public OrganizerResponse getProfile(UserInfo userInfo) {
        return organizerService.getById(userInfo.getUserId());
    }

    @Override
    public OrganizerResponse changeContactPerson(UserInfo userInfo, ContactPersonChangeRequest contactPersonChangeRequest) {
        return organizerService.updateContactPerson(userInfo.getUserId(), contactPersonChangeRequest.contactPerson());
    }

    @Override
    public OrganizerResponse changeWebsiteUrl(UserInfo userInfo, WebsiteChangeRequest websiteChangeRequest) {
        return organizerService.updateWebsiteUrl(userInfo.getUserId(), websiteChangeRequest.websiteUrl());
    }
}
