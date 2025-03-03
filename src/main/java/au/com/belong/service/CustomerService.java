package au.com.belong.service;

import au.com.belong.domain.Customer;
import au.com.belong.domain.Phone;
import au.com.belong.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

public interface CustomerService {

    Set<Phone> findPhonesByCustomer(UUID id);

    @Service
    @Slf4j
    class Default implements CustomerService {
        @Autowired
        private CustomerRepository customerRepository;

        public Set<Phone> findPhonesByCustomer(UUID id) {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));
            return customer.getPhones();
        }
    }
}
