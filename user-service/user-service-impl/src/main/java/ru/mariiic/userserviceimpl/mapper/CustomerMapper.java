package ru.mariiic.userserviceimpl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mariic.userserviceapi.response.CustomerResponse;
import ru.mariiic.userserviceimpl.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "userId", source = "id")
    CustomerResponse toResponse(Customer customer);
}
