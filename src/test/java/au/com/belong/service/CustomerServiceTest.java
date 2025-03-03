package au.com.belong.service;

import au.com.belong.domain.Customer;
import au.com.belong.domain.Phone;
import au.com.belong.domain.enums.Status;
import au.com.belong.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService = new CustomerService.Default();

    @Test
    public void findPhonesByCustomer_returnExpectedPhoneEntriesOfCustomer() {
        //given
        UUID id = UUID.randomUUID();

        Customer customer = Customer.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .dateCreated(LocalDateTime.now())
                .build();

        Phone phone1 = Phone.builder()
                .id(UUID.randomUUID())
                .customer(customer)
                .number("0452218337")
                .dateCreated(LocalDateTime.now())
                .status(Status.ACTIVATED)
                .build();

        Phone phone2 = Phone.builder()
                .id(UUID.randomUUID())
                .customer(customer)
                .number("0452218338")
                .dateCreated(LocalDateTime.now())
                .status(Status.DEACTIVATED)
                .build();

        Set<Phone> expectedPhoneEntries = Set.of(phone1, phone2);
        customer.setPhones(expectedPhoneEntries);

        //when
        when(customerRepository.findById(eq(id))).thenReturn(Optional.of(customer));

        //then
        Set<Phone> actualPhoneEntries = customerService.findPhonesByCustomer(id);
        verify(customerRepository).findById(eq(id));
        Assertions.assertEquals(expectedPhoneEntries, actualPhoneEntries);
    }

    @Test
    public void findPhonesByCustomer_expectEntityNotFoundException() {
        //given
        UUID id = UUID.randomUUID();

        //when
        when(customerRepository.findById(id)).thenThrow(EntityNotFoundException.class);

        //then
        Assertions.assertThrows(EntityNotFoundException.class, () -> customerService.findPhonesByCustomer(id));
    }
}
