package au.com.belong.controller;

import au.com.belong.domain.Phone;
import au.com.belong.domain.enums.Status;
import au.com.belong.service.PhoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class PhoneControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    PhoneService phoneService;

    @Value("${belong.api.key}")
    private String API_KEY;

    @Test
    public void throwUnauthorizedAccess_whenNoAPIKeyProvided() throws Exception {
        mockMvc.perform(get("/phones"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void findAll_returnStatusOK() throws Exception {
        //given
        Set<Phone> expectedPhoneEntries = Set.of(Phone.builder()
                        .number("123456789")
                        .build(),
                Phone.builder()
                        .number("987654321")
                        .build());

        //when
        when(phoneService.findAll()).thenReturn(expectedPhoneEntries);

        //then
        MvcResult result = mockMvc.perform(get("/phones")
                        .header("X-API-KEY", API_KEY))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        verify(phoneService).findAll();
        Assertions.assertEquals(new ObjectMapper().writeValueAsString(expectedPhoneEntries), result.getResponse().getContentAsString());
    }

    @Test
    public void activatePhoneStatus_returnStatusOK() throws Exception {
        //given
        final UUID id = UUID.randomUUID();
        Phone expectedPhone = Phone.builder()
                        .number("123456789")
                        .status(Status.ACTIVATED)
                        .build();

        //when
        when(phoneService.setStatus(eq(id), eq(Status.ACTIVATED))).thenReturn(expectedPhone);

        //then
        MvcResult result = mockMvc.perform(put("/phones/"+ id + "?status=" + Status.ACTIVATED)
                        .header("X-API-KEY", API_KEY))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        verify(phoneService).setStatus(eq(id), eq(Status.ACTIVATED));
        Assertions.assertEquals(new ObjectMapper().writeValueAsString(expectedPhone), result.getResponse().getContentAsString());
    }

    @Test
    public void activatePhoneStatus_returnStatusNotFound() throws Exception {
        //given
        UUID id = UUID.randomUUID();

        //when
        when(phoneService.setStatus(eq(id), eq(Status.ACTIVATED))).thenThrow(EntityNotFoundException.class);

        //then
        mockMvc.perform(put("/phones/"+ id + "?status=" + Status.ACTIVATED)
                        .header("X-API-KEY", API_KEY))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(phoneService).setStatus(eq(id), eq(Status.ACTIVATED));
    }

    @Test
    public void activatePhoneStatusToUNKNOWN_returnStatusBadRequest() throws Exception {
        //given
        UUID id = UUID.randomUUID();

        //then
        String UNKNOWN = "UNKNOWN";
        mockMvc.perform(put("/phones/"+ id + "?status=" + UNKNOWN)
                        .header("X-API-KEY", API_KEY))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}