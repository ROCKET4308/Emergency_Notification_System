package com.notificationservice.service;

import com.notificationservice.entity.MessageTemplate;
import com.notificationservice.request.MessageTemplateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageTemplateService {

    //TODO:
    public String createMessageTemplate(MessageTemplateRequest messageTemplateRequest) {
        return "";
    }

    //TODO:
    public MessageTemplate getMessageTemplate(String templateName) {
        return new MessageTemplate();
    }

    //TODO:
    public MessageTemplate updeteMessageTemplate(String templateName) {
        return new MessageTemplate();
    }

    //TODO:
    public MessageTemplate deleteMessageTemplate(String templateName) {
        return new MessageTemplate();
    }

    //TODO: sent message using messageService
    public Map<String, String> sentMessage(String templateName) {
        return new HashMap<>();
    }
}