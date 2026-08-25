package ru.mariiic.userserviceimpl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.mariic.userserviceapi.CustomerApi;
import ru.mariic.userserviceapi.response.CustomerResponse;
import ru.mariiic.securitystarter.dto.UserInfo;
import ru.mariiic.userserviceimpl.service.CustomerService;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {
    private final CustomerService customerService;

    @Override
    public CustomerResponse getProfile(UserInfo userInfo) {
        return customerService.getById(userInfo.getUserId());
    }
}
