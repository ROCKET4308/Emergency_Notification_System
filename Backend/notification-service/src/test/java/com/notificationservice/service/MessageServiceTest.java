package com.notificationservice.service;

import static org.junit.jupiter.api.Assertions.*;

import com.notificationservice.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.notificationservice.entity.MessageStatus;
import com.notificationservice.repository.MessageStatusRepository;
import com.notificationservice.request.MessageRequest;
import com.notificationservice.request.MessageTemplateRequest;
import com.notificationservice.twilio.Sender;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class MessageServiceTest {

    @Mock
    private Sender sender;

    @Mock
    private MessageStatusRepository messageStatusRepository;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSentMessage() throws IOException {
        MessageRequest request = new MessageRequest();
        request.setRecipientContacts(Arrays.asList("test@example.com"));
        when(sender.sentMailMessage(any(), any())).thenReturn("messageId_____________");
        Map<String, String> result = messageService.sentMessage(request);
        assertEquals("Delivered", result.get("test@example.com"));
    }

    @Test
    public void testSentMessageTemplate() throws IOException {
        MessageTemplateRequest request = new MessageTemplateRequest();
        request.setRecipientContacts(Arrays.asList("test@example.com"));
        User user = new User();
        user.setEmail("test@mail.com");
        when(sender.sentMailMessage(any(), any())).thenReturn("messageId_____________");
        Map<String, String> result = messageService.sentMessage(request, user);
        assertEquals("Delivered", result.get("test@example.com"));
    }

    @Test
    public void testSentPhoneMessage() {
        MessageStatus message = messageService.sentPhoneMessage("Test message", "+1234567890");
        when(sender.sentPhoneMessage(any(), any())).thenReturn("");
        assertEquals("Not Delivered", message.getStatus());
    }

    @Test
    public void testSentMailMessage() throws IOException {
        MessageStatus message = messageService.sentMailMessage("Test message", "test@example.com");
        when(sender.sentMailMessage(any(), any())).thenReturn("");
        assertEquals("Not Delivered", message.getStatus());
    }

    @Test
    public void testIsValidEmail() {
        assertTrue(messageService.isValidEmail("test@example.com"));
        assertFalse(messageService.isValidEmail("invalid-email"));
    }

    @Test
    public void testIsValidPhoneNumber() {
        assertTrue(messageService.isValidPhoneNumber("+1234567890"));
        assertFalse(messageService.isValidPhoneNumber("1234567890"));
    }

}

