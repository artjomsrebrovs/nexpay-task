package com.nexpay.task.rest.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexpay.task.cache.CountriesCache;
import com.nexpay.task.rest.request.PhoneNumber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CountriesControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountriesCache countriesCache;

    @Test
    void testGetCountryByCode() throws Exception {
        when(countriesCache.get("371")).thenReturn(Collections.singletonList("Latvia"));

        mockMvc.perform(post("/country")
                .content(asJsonString(new PhoneNumber("371", "123456789")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.countries", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countries[0]").value("Latvia"));
    }

    @Test
    void testGetCountryByCodeMultipleCountries() throws Exception {
        when(countriesCache.get("255")).thenReturn(Arrays.asList("Tanzania", "Zanzibar"));

        mockMvc.perform(post("/country")
                .content(asJsonString(new PhoneNumber("255", "123456789")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.countries", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countries[0]").value("Tanzania"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countries[1]").value("Zanzibar"));
    }

    @Test
    void testGetCountryByCodeNonNumericCode() throws Exception {
        when(countriesCache.get("255")).thenReturn(Arrays.asList("Tanzania", "Zanzibar"));

        mockMvc.perform(post("/country")
                .content(asJsonString(new PhoneNumber("255X", "123456789")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("Country code should be numeric"));
    }

    @Test
    void testGetCountryByCodeNonNumericNumber() throws Exception {
        when(countriesCache.get("255")).thenReturn(Arrays.asList("Tanzania", "Zanzibar"));

        mockMvc.perform(post("/country")
                .content(asJsonString(new PhoneNumber("255", "123456789X")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("Phone number should be numeric"));
    }

    @Test
    void testGetCountryByCodeCodeTooShort() throws Exception {
        when(countriesCache.get("255")).thenReturn(Arrays.asList("Tanzania", "Zanzibar"));

        mockMvc.perform(post("/country")
                .content(asJsonString(new PhoneNumber("2", "123456789")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("Country code is too short"));
    }

    @Test
    void testGetCountryByCodeCodeTooLong() throws Exception {
        when(countriesCache.get("255")).thenReturn(Arrays.asList("Tanzania", "Zanzibar"));

        mockMvc.perform(post("/country")
                .content(asJsonString(new PhoneNumber("255555", "123456789")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("Country code is too long"));
    }

    @Test
    void testGetCountryByCodeNumberTooShort() throws Exception {
        when(countriesCache.get("255")).thenReturn(Arrays.asList("Tanzania", "Zanzibar"));

        mockMvc.perform(post("/country")
                .content(asJsonString(new PhoneNumber("255", "123")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("Phone number is too short"));
    }

    @Test
    void testGetCountryByCodeNumberTooLong() throws Exception {
        when(countriesCache.get("255")).thenReturn(Arrays.asList("Tanzania", "Zanzibar"));

        mockMvc.perform(post("/country")
                .content(asJsonString(new PhoneNumber("255", "123456789123456789")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("Phone number is too long"));
    }

    private String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}