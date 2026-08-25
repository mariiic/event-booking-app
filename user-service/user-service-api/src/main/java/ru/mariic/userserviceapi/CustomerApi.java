package ru.mariic.userserviceapi;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.mariic.userserviceapi.response.CustomerResponse;
import ru.mariiic.securitystarter.dto.UserInfo;

@RequestMapping("api/v1/customer")
@PreAuthorize("hasRole('CUSTOMER')")
public interface CustomerApi {
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    CustomerResponse getProfile(@AuthenticationPrincipal UserInfo userInfo);
}
