package com.notificationservice.repository;

import com.notificationservice.entity.MessageTemplate;
import com.notificationservice.entity.Role;
import com.notificationservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MessageTemplateRepositoryTest {
    @Autowired
    private MessageTemplateRepository messageTemplateRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserAndTemplateName() {
        User user = new User(1, "user@mail.com", "12345", Role.USER);
        userRepository.save(user);
        String templateName = "TemplateName";
        MessageTemplate messageTemplate = new MessageTemplate(1, user, templateName, "messageText", "recipientContact");
        MessageTemplate savedMessageTemplate = messageTemplateRepository.save(messageTemplate);
        MessageTemplate returnMessageTemplate = messageTemplateRepository.findByUserAndTemplateName(user, templateName).orElseThrow(() -> new IllegalArgumentException("Template with this name is not exist"));
        assertEquals(savedMessageTemplate, returnMessageTemplate);
    }

    @Test
    void findAllByUserAndTemplateName() {
        User user = new User(1, "user@mail.com", "12345", Role.USER);
        userRepository.save(user);
        String templateName = "TemplateName";
        MessageTemplate messageTemplate1 = new MessageTemplate(1, user, templateName, "messageText", "recipientContact");
        MessageTemplate messageTemplate2 = new MessageTemplate(2, user, templateName, "messageText", "recipientContact2");
        messageTemplateRepository.save(messageTemplate1);
        messageTemplateRepository.save(messageTemplate2);

        List<MessageTemplate> messageTemplateList = new ArrayList<>();
        messageTemplateList.add(messageTemplate1);
        messageTemplateList.add(messageTemplate2);

        List<MessageTemplate> returnMessageTemplateList = messageTemplateRepository.findAllByUserAndTemplateName(user, templateName);
        if(returnMessageTemplateList.isEmpty()){
            throw new IllegalArgumentException("Template with this name is not exist");
        }
        assertEquals(messageTemplateList, returnMessageTemplateList);
    }

    @Test
    void deleteAllByUserAndTemplateName() {
        User user = new User(1, "user@mail.com", "12345", Role.USER);
        userRepository.save(user);
        String templateName = "TemplateName";
        MessageTemplate messageTemplate1 = new MessageTemplate(1, user, templateName, "messageText", "recipientContact");
        MessageTemplate messageTemplate2 = new MessageTemplate(2, user, templateName, "messageText", "recipientContact2");
        messageTemplate1 = messageTemplateRepository.save(messageTemplate1);
        messageTemplate2 = messageTemplateRepository.save(messageTemplate2);

        List<MessageTemplate> messageTemplateList = new ArrayList<>();
        messageTemplateList.add(messageTemplate1);
        messageTemplateList.add(messageTemplate2);

        messageTemplateRepository.deleteAllByUserAndTemplateName(user, templateName);

        List<MessageTemplate> returnMessageTemplateList = messageTemplateRepository.findAllByUserAndTemplateName(user, templateName);
        assertTrue(returnMessageTemplateList.isEmpty());
    }

}