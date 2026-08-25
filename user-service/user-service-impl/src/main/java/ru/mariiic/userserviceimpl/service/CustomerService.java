package ru.mariiic.userserviceimpl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mariic.userserviceapi.response.CustomerResponse;
import ru.mariiic.userserviceimpl.exception.CustomerNotFoundException;
import ru.mariiic.userserviceimpl.mapper.CustomerMapper;
import ru.mariiic.userserviceimpl.model.Customer;
import ru.mariiic.userserviceimpl.repository.CustomerRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Transactional
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public CustomerResponse getById(UUID userId) {
        Customer customer = customerRepository.findById(userId).orElseThrow(() -> new CustomerNotFoundException("Customer with id %s not found".formatted(userId)));
        return customerMapper.toResponse(customer);
    }

    @Transactional
    public void updateEmail(UUID userId, String email) {
        if (customerRepository.findById(userId).isEmpty()) {
            throw new CustomerNotFoundException("Customer with id %s not found".formatted(userId));
        }
        customerRepository.updateEmail(userId, email);
    }
}
