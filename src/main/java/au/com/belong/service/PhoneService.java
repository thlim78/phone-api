package au.com.belong.service;

import au.com.belong.domain.Phone;
import au.com.belong.domain.enums.Status;
import au.com.belong.repository.PhoneRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public interface PhoneService {

    Set<Phone> findAll();

    Phone setStatus(UUID id, Status status);

    @Service
    @Slf4j
    class Default implements PhoneService {
        @Autowired
        private PhoneRepository phoneRepository;

        public Set<Phone> findAll() {
            return phoneRepository.findAll()
                    .stream()
                    .collect(Collectors.toSet());
        }

        public Phone setStatus(UUID id, Status status) {
            Phone phone = phoneRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Phone not found with id " + id));
            phone.setStatus(status);
            return phoneRepository.save(phone);
        }
    }
}
