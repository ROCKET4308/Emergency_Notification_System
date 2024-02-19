package com.notificationservice.service;

import com.notificationservice.entity.MessageTemplate;
import com.notificationservice.entity.User;
import com.notificationservice.repository.MessageTemplateRepository;
import com.notificationservice.repository.UserRepository;
import com.notificationservice.request.MessageTemplateRequest;
import com.notificationservice.response.MessageTemplateResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import java.util.*;

public class MessageTemplateServiceTest {

    private final MessageTemplateRepository messageTemplateRepository = Mockito.mock(MessageTemplateRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final JwtService jwtService = Mockito.mock(JwtService.class);
    private final MessageService messageService = Mockito.mock(MessageService.class);
    private final MessageTemplateService messageTemplateService = new MessageTemplateService(messageService, messageTemplateRepository, userRepository, jwtService);

    @Test
    void testCreateMessageTemplate() {
        MessageTemplateRequest request = new MessageTemplateRequest("Template", "Message", Arrays.asList("recipient1@mail.com", "recipient2@mail.com"));
        String authorizationHeader = "Bearer token";
        User user = new User();
        user.setEmail("test@example.com");
        Mockito.when(jwtService.extractUsername("token")).thenReturn("test@example.com");
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        Mockito.when(messageTemplateRepository.findByUserAndTemplateName(user, "Template")).thenReturn(Optional.empty());

        MessageTemplateResponse response = messageTemplateService.createMessageTemplate(request, authorizationHeader);

        assertEquals("Template", response.getTemplateName());
        assertEquals("Message", response.getMessageText());
        assertEquals(Arrays.asList("recipient1@mail.com", "recipient2@mail.com"), response.getRecipientContacts());
    }

    @Test
    void testGetMessageTemplate() {
        String templateName = "Template";
        String authorizationHeader = "Bearer token";
        User user = new User();
        user.setEmail("test@example.com");
        Mockito.when(jwtService.extractUsername("token")).thenReturn("test@example.com");
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        Mockito.when(messageTemplateRepository.findAllByUserAndTemplateName(user, "Template")).thenReturn(Arrays.asList(
                new MessageTemplate(1, user, "Template", "Message","recipient@mail.com")
        ));

        MessageTemplateResponse response = messageTemplateService.getMessageTemplate(templateName, authorizationHeader);

        assertEquals("Template", response.getTemplateName());
        assertEquals("Message", response.getMessageText());
        assertEquals(Arrays.asList("recipient@mail.com"), response.getRecipientContacts());
    }


    @Test
    void testDeleteMessageTemplate() {
        String templateName = "Template";
        String authorizationHeader = "Bearer token";
        User user = new User();
        user.setEmail("test@example.com");
        Mockito.when(jwtService.extractUsername("token")).thenReturn("test@example.com");
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        MessageTemplate messageTemplate = new MessageTemplate(1, user, templateName, "Message","recipient@mail.com");
        messageTemplateRepository.save(messageTemplate);
        Mockito.when(messageTemplateRepository.findByUserAndTemplateName(user, templateName)).thenReturn(Optional.of(messageTemplate));
        Mockito.when(messageTemplateRepository.deleteAllByUserAndTemplateName(user, templateName)).thenReturn(Arrays.asList(messageTemplate));

        String response = messageTemplateService.deleteMessageTemplate(templateName, authorizationHeader);

        assertEquals("Successfully deleted template: " + templateName, response);
    }

    @Test
    void testSentMessage() {
        String templateName = "Template";
        String authorizationHeader = "Bearer token";
        User user = new User();
        user.setEmail("test@example.com");
        Mockito.when(jwtService.extractUsername("token")).thenReturn("test@example.com");
        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        Mockito.when(messageTemplateRepository.findAllByUserAndTemplateName(user, "Template")).thenReturn(Arrays.asList(
                new MessageTemplate(1, user, "Template", "Message","recipient@mail.com")
        ));
        Map<String, String> messageResponse = new HashMap<>();
        messageResponse.put("recipient@mail.com", "Delivered");
        Mockito.when(messageService.sentMessage(Mockito.any(), Mockito.any())).thenReturn(messageResponse);

        Map<String, String> response = messageTemplateService.sentMessage(templateName, authorizationHeader);

        assertEquals("Delivered", response.get("recipient@mail.com"));
    }
}
