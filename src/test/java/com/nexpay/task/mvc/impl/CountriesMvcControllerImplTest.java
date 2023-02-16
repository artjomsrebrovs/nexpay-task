package com.nexpay.task.mvc.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CountriesMvcControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testViewBooks() throws Exception {

        final MvcResult mvcResult = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        final ModelAndView modelAndView = mvcResult.getModelAndView();
        assertThat("Model and view response should not be null", modelAndView, is(notNullValue()));

        final String response = modelAndView.getViewName();
        assertThat("Vie name response should not be null", response, is("index"));
    }
}