package com.messagetemplateservice.service;

import com.messagetemplateservice.entity.MessageTemplate;
import com.messagetemplateservice.repository.MessageTemplateRepository;
import com.messagetemplateservice.request.MessageTemplateRequest;
import com.messagetemplateservice.response.MessageTemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageTemplateService {
    private final MessageTemplateRepository messageTemplateRepository;



    public MessageTemplateResponse createMessageTemplate(MessageTemplateRequest messageTemplateRequest, String authorizationHeader) {
        String email = getEmailByToken(authorizationHeader);
        if(messageTemplateRepository.findByUserAndTemplateName(email, messageTemplateRequest.getTemplateName()).isPresent()){
            throw new IllegalArgumentException("Template with that name " + messageTemplateRequest.getTemplateName() + " is presented, change name");
        }
        for(String recipientContact : messageTemplateRequest.getRecipientContacts()){
            MessageTemplate messageTemplate = MessageTemplate.builder().email(email).templateName(messageTemplateRequest.getTemplateName())
                    .recipientContact(recipientContact).messageText(messageTemplateRequest.getMessageText()).build();
            messageTemplateRepository.save(messageTemplate);
        }

        return MessageTemplateResponse.builder().templateName(messageTemplateRequest.getTemplateName()).messageText(messageTemplateRequest.getMessageText())
                .recipientContacts(messageTemplateRequest.getRecipientContacts()).build();
    }

    public MessageTemplateResponse getMessageTemplate(String templateName, String authorizationHeader) {
        String email = getEmailByToken(authorizationHeader);
        List<MessageTemplate> messageTemplateList = messageTemplateRepository.findAllByUserAndTemplateName(email, templateName);
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

    public MessageTemplateResponse updateMessageTemplate(String templateName, MessageTemplateRequest messageTemplateRequest, String authorizationHeader) {
       deleteMessageTemplate(templateName, authorizationHeader);
       return createMessageTemplate(messageTemplateRequest, authorizationHeader);
    }


    public String deleteMessageTemplate(String templateName, String authorizationHeader) {
        String email = getEmailByToken(authorizationHeader);
        if(messageTemplateRepository.findByUserAndTemplateName(email, templateName).isEmpty()){
            throw new IllegalArgumentException("Error to find template with name "+ templateName);
        }
        List<MessageTemplate> messageTemplateDeletedList = messageTemplateRepository.deleteAllByUserAndTemplateName(email, templateName);
        if(!messageTemplateDeletedList.isEmpty()){
            return "Successfully deleted template: " + templateName;
        }else{
            return "Cause some error";
        }
    }

    //TODO: return email, get email by api request to auth-service
    public String getEmailByToken(String authorizationHeader){
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        return jwtService.extractUsername(jwtToken);
    }

}
