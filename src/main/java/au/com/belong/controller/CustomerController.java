package au.com.belong.controller;

import au.com.belong.domain.Phone;
import au.com.belong.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@Slf4j
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/customers/{id}/phones")
    public Set<Phone> findPhonesByCustomer(@PathVariable UUID id) {
        return customerService.findPhonesByCustomer(id);
    }
}
