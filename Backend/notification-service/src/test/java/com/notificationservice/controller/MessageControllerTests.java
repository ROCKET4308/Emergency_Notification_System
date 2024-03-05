package com.notificationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationservice.request.MessageRequest;
import com.notificationservice.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
public class MessageControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MessageService messageService;

    @Test
    public void testSendMessage() throws Exception {
        // Prepare data
        List<String> recipientContacts = new ArrayList<>();
        recipientContacts.add("test@mail.com");
        MessageRequest messageRequest = new MessageRequest("Hello",recipientContacts);
        Map<String, String> response = new HashMap<>();
        response.put("test@mail.com", "Delivered");

        // Mock service method
        when(messageService.sentMessage(any(MessageRequest.class))).thenReturn(response);

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/message/sent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(messageRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("test@mail.com").value("Delivered"));
    }
}

