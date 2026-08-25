package ru.mariiic.userserviceimpl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mariic.userserviceapi.response.OrganizerResponse;
import ru.mariiic.userserviceimpl.exception.OrganizerNotFoundException;
import ru.mariiic.userserviceimpl.mapper.OrganizerMapper;
import ru.mariiic.userserviceimpl.model.Organizer;
import ru.mariiic.userserviceimpl.repository.OrganizerRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizerService {
    private final OrganizerRepository organizerRepository;

    private final OrganizerMapper organizerMapper;

    @Transactional
    public OrganizerResponse getById(UUID id) {
        Organizer organizer = organizerRepository.findById(id).orElseThrow(() -> new OrganizerNotFoundException("organizer with id %s not found".formatted(id)));
        return organizerMapper.toResponse(organizer);
    }

    @Transactional
    public Organizer create(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    @Transactional
    public void updateEmail(UUID userId, String email) {
        if (organizerRepository.findById(userId).isEmpty()) {
            throw new OrganizerNotFoundException("organizer with id %s not found".formatted(userId));
        }
        organizerRepository.updateEmail(userId, email);
    }

    @Transactional
    public OrganizerResponse updateContactPerson(UUID userId, String contactPerson) {
        Organizer organizer = organizerRepository.findById(userId).orElseThrow(() -> new OrganizerNotFoundException("organizer with id %s not found".formatted(userId)));
        organizer.changeContactPerson(contactPerson);
        return organizerMapper.toResponse(organizer);
    }

    @Transactional
    public OrganizerResponse updateWebsiteUrl(UUID userId, String websiteUrl) {
        Organizer organizer = organizerRepository.findById(userId).orElseThrow(() -> new OrganizerNotFoundException("organizer with id %s not found".formatted(userId)));
        organizer.addWebsiteUrl(websiteUrl);
        return organizerMapper.toResponse(organizer);
    }
}
