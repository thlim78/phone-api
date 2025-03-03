package au.com.belong.controller;

import au.com.belong.domain.Phone;
import au.com.belong.service.CustomerService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    CustomerService customerService;

    @Value("${belong.api.key}")
    private String API_KEY;

    @Test
    public void throwUnauthorizedAccess_whenNoAPIKeyProvided() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void findPhonesByCustomer_returnStatusOK() throws Exception {
        //given
        UUID id = UUID.randomUUID();
        Set<Phone> expectedPhoneEntries = Set.of(Phone.builder()
                .number("123456789")
                .build());

        //when
        when(customerService.findPhonesByCustomer(eq(id))).thenReturn(expectedPhoneEntries);

        //then
        MvcResult result = mockMvc.perform(get("/customers/"+ id + "/phones")
                        .header("X-API-KEY", API_KEY))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        verify(customerService).findPhonesByCustomer(any(UUID.class));
        Assertions.assertEquals(new ObjectMapper().writeValueAsString(expectedPhoneEntries), result.getResponse().getContentAsString());
    }

    @Test
    public void findPhonesByCustomer_returnStatusNotFound() throws Exception {
        //given
        UUID id = UUID.randomUUID();

        //when
        when(customerService.findPhonesByCustomer(eq(id))).thenThrow(EntityNotFoundException.class);

        //then
        mockMvc.perform(get("/customers/"+ id + "/phones")
                        .header("X-API-KEY", API_KEY))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(customerService).findPhonesByCustomer(any(UUID.class));
    }

}