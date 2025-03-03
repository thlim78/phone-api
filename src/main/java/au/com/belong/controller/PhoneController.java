package au.com.belong.controller;

import au.com.belong.domain.Phone;
import au.com.belong.domain.enums.Status;
import au.com.belong.service.PhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@Slf4j
public class PhoneController {

    @Autowired
    PhoneService phoneService;

    @GetMapping("/phones")
    public Set<Phone> findAll() {
        return phoneService.findAll();
    }

    // From security standpoint, best to use UUID to activate phone status.
    // UUID is random, and it's hard to guess.
    @PutMapping("/phones/{id}")
    public Phone setStatus(@PathVariable UUID id, @RequestParam(value="status") Status status) {
        return phoneService.setStatus(id, status);
    }
}
