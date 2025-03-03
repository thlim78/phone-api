package au.com.belong.service;

import au.com.belong.domain.Customer;
import au.com.belong.domain.Phone;
import au.com.belong.domain.enums.Status;
import au.com.belong.repository.PhoneRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PhoneServiceTest {

    @Mock
    PhoneRepository phoneRepository;

    @InjectMocks
    PhoneService phoneService = new PhoneService.Default();

    @Test
    public void findAll_returnExpectedPhoneEntries() {
        //given
        Phone phone1 = Phone.builder()
                .id(UUID.randomUUID())
                .customer(Customer.builder().build())
                .number("0452218337")
                .dateCreated(LocalDateTime.now())
                .status(Status.ACTIVATED)
                .build();

        Phone phone2 = Phone.builder()
                .id(UUID.randomUUID())
                .customer(Customer.builder().build())
                .number("0452218338")
                .dateCreated(LocalDateTime.now())
                .status(Status.DEACTIVATED)
                .build();

        //when
        List<Phone> expectedPhoneEntries = List.of(phone1, phone2);
        when(phoneRepository.findAll()).thenReturn(expectedPhoneEntries);

        //then
        phoneService.findAll();
        verify(phoneRepository).findAll();
        Assertions.assertEquals(expectedPhoneEntries.stream().collect(Collectors.toSet()), phoneService.findAll());
    }

    @Test
    public void findAll_expectEmptyPhoneEntries() {
        //when
        List<Phone> emptyPhoneEntries = List.of();
        when(phoneRepository.findAll()).thenReturn(emptyPhoneEntries);

        //then
        phoneService.findAll();
        verify(phoneRepository).findAll();
        Assertions.assertEquals(emptyPhoneEntries.stream().collect(Collectors.toSet()), phoneService.findAll());
    }

    @Test
    public void setStatus_expectStatusChangedToActivated() {
        //given
        UUID id = UUID.randomUUID();
        Phone phone1 = Phone.builder()
                .id(id)
                .customer(Customer.builder().build())
                .number("0452218337")
                .dateCreated(LocalDateTime.now())
                .status(null)
                .build();

        Phone phone2 = Phone.builder()
                .id(id)
                .customer(Customer.builder().build())
                .number("0452218337")
                .dateCreated(LocalDateTime.now())
                .status(Status.ACTIVATED)
                .build();

        //when
        when(phoneRepository.findById(id)).thenReturn(Optional.of(phone1));
        when(phoneRepository.save(any(Phone.class))).thenReturn(phone2);

        //then
        Phone actualPhone = phoneService.setStatus(id, Status.ACTIVATED);
        verify(phoneRepository).findById(eq(id));
        verify(phoneRepository).save(any(Phone.class));
        Assertions.assertEquals(actualPhone.getStatus(), Status.ACTIVATED);
    }

    @Test
    public void setStatus_expectEntityNotFoundException() {
        //given
        UUID id = UUID.randomUUID();

        //when
        when(phoneRepository.findById(id)).thenThrow(EntityNotFoundException.class);

        //then
        Assertions.assertThrows(EntityNotFoundException.class, () -> phoneService.setStatus(id, Status.ACTIVATED));
    }
}
