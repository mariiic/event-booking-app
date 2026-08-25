package ru.mariiic.userserviceimpl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mariic.userserviceapi.response.OrganizerResponse;
import ru.mariiic.userserviceimpl.model.Organizer;

@Mapper(componentModel = "spring")
public interface OrganizerMapper {
    @Mapping(target = "userId", source = "id")
    OrganizerResponse toResponse(Organizer organizer);
}
