package com.example.otclients.controller.configuration;

import com.example.otclients.controller.AbstractControllerTest;
import com.example.otclients.dto.request.configuration.CountryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.oneOf;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
public class CountryControllerTest extends AbstractControllerTest {


    @Test
    void testFindAll() throws Exception {
//        String token = authenticationService.generateJWT(
//                authenticationService.generateToken(
//                        userService.findByUsernameOrMail("admin")));
//
//        Assertions.assertNotNull(token);

        mockMvc.perform(
                        get("/api/v1/country/fetch"))
                .andExpect(status().is(
                        oneOf(HttpStatus.OK.value(), HttpStatus.UNAUTHORIZED.value())));
    }

    @Test
    void postTest() throws Exception {
        String json2 = new ObjectMapper().writeValueAsString(
                CountryRequest.builder()
                        .code("123")
                        .name("Test")
                        .placeId("1232131231")
                        .build());

        mockMvc.perform(
                post("/api/v1/country").contentType(APPLICATION_JSON_VALUE).content(json2)).andExpect(
                status().is(
                        oneOf(HttpStatus.OK.value(), HttpStatus.UNAUTHORIZED.value())));
    }

    @Test
    void getTest() throws Exception {
        mockMvc.perform(get("/api/v1/country" + "?id=" + 1)).andExpect(status().is(
                oneOf(HttpStatus.OK.value(), HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.NOT_FOUND.value())));
    }

    @Test
    void findall() throws Exception {
        mockMvc.perform(
                        get("/api/v1/country/findall"))
                .andExpect(status().is(
                        oneOf(HttpStatus.OK.value(), HttpStatus.UNAUTHORIZED.value())));
    }

    @Test
    void countall() throws Exception {
        mockMvc.perform(get("/api/v1/country/countall")).andExpect(status().is(
                oneOf(HttpStatus.OK.value(), HttpStatus.UNAUTHORIZED.value())));
    }
}
