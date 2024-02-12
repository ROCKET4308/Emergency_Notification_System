package com.notificationservice.service;

import com.notificationservice.entity.MessageTemplate;
import com.notificationservice.entity.User;
import com.notificationservice.repository.MessageTemplateRepository;
import com.notificationservice.repository.UserRepository;
import com.notificationservice.request.MessageTemplateRequest;
import com.notificationservice.response.MessageTemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageTemplateService {
    private final MessageService messageService;
    private final MessageTemplateRepository messageTemplateRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    //TODO: make function to check is user exist and return him


    public MessageTemplateResponse createMessageTemplate(MessageTemplateRequest messageTemplateRequest, String authorizationHeader) {
        User user = getUserByToken(authorizationHeader);
        if(messageTemplateRepository.findByUserAndTemplateName(user, messageTemplateRequest.getTemplateName()).isPresent()){
            throw new IllegalArgumentException("Template with that name " + messageTemplateRequest.getTemplateName() + " is presented, change name");
        }
        for(String recipientContact : messageTemplateRequest.getRecipientContacts()){
            MessageTemplate messageTemplate = MessageTemplate.builder().user(user).templateName(messageTemplateRequest.getTemplateName())
                    .recipientContact(recipientContact).messageText(messageTemplateRequest.getMessageText()).build();
            messageTemplateRepository.save(messageTemplate);
        }

        return MessageTemplateResponse.builder().templateName(messageTemplateRequest.getTemplateName()).messageText(messageTemplateRequest.getMessageText())
                .recipientContacts(messageTemplateRequest.getRecipientContacts()).build();
    }

    public MessageTemplateResponse getMessageTemplate(String templateName, String authorizationHeader) {
        User user = getUserByToken(authorizationHeader);
        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAllByUserAndTemplateName(user, templateName);
        if(messageTemplateList.isEmpty()){
            throw new IllegalArgumentException("There is no message template with that name" + templateName);
        }

        List<String> recipientContacts = new ArrayList<>();
        for(MessageTemplate messageTemplate : messageTemplateList){
            recipientContacts.add(messageTemplate.getRecipientContact());
        }

        return MessageTemplateResponse.builder().templateName(messageTemplateList.get(0).getTemplateName())
                .messageText(messageTemplateList.get(0).getMessageText()).recipientContacts(recipientContacts).build();
    }

    //TODO:
    public MessageTemplateResponse updateMessageTemplate(String templateName) {
        return new MessageTemplateResponse();
    }

    //TODO:
    public String deleteMessageTemplate(String templateName) {
        return "";
    }

    //TODO: sent message using messageService make massage request to use it in message service
    public Map<String, String> sentMessage(String templateName) {

        return new HashMap<>();
    }

    public User getUserByToken(String authorizationHeader){
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        String email = jwtService.extractUsername(jwtToken);
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User with this email "+ email + " is not exist"));
    }

}
