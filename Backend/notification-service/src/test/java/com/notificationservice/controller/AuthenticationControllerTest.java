package com.notificationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationservice.request.AuthenticationRequest;
import com.notificationservice.request.RegisterRequest;
import com.notificationservice.response.AuthenticationResponse;
import com.notificationservice.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(AuthenticationController.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void testRegister() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("username", "password");
        AuthenticationResponse expectedResponse = new AuthenticationResponse("token");

        given(authenticationService.register(any(RegisterRequest.class))).willReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token"));
    }

    @Test
    public void testAuthenticate() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("username", "password");
        AuthenticationResponse expectedResponse = new AuthenticationResponse("token");

        given(authenticationService.authenticate(any(AuthenticationRequest.class))).willReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token"));
    }
}