package com.notificationservice.service;

import com.notificationservice.entity.MessageStatus;
import com.notificationservice.repository.MessageStatusRepository;
import com.notificationservice.request.MessageRequest;
import com.notificationservice.request.MessageTemplateRequest;
import com.notificationservice.twilio.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final Sender sender;
    private final MessageStatusRepository messageStatusRepository;

    //TODO: change function save massage in massage status db
    public Map<String, String> sentMessage(MessageRequest messageRequest) {
        Map<String, String> deliveryStatusMap = new HashMap<>();
        List<String> contacts = messageRequest.getContacts();
        for(String contact : contacts){
            if(isValidEmail(contact)){
                sentMailMessage(messageRequest.getMessageText(), contact);
            }
            else if(isValidPhoneNumber(contact)){
                sentPhoneMessage(messageRequest.getMessageText(), contact);
            }
            else{
                throw new IllegalArgumentException("Not valid contact: " + contact);
            }
        }
        return deliveryStatusMap;
    }

    //TODO: change function
    public Map<String, String> sentMessage(MessageTemplateRequest messageTemplateRequest) {
        Map<String, String> deliveryStatusMap = new HashMap<>();
        List<String> contacts = messageTemplateRequest.getContacts();
        for(String contact : contacts){
            if(isValidEmail(contact)){
                sentMailMessage(messageTemplateRequest.getMessageText(), contact);
            }
            else if(isValidPhoneNumber(contact)){
               sentPhoneMessage(messageTemplateRequest.getMessageText(), contact);
            }
            else{
                throw new IllegalArgumentException("Not valid contact: " + contact);
            }
        }
        return deliveryStatusMap;
    }

    public MessageStatus sentPhoneMessage(String messageText, String phoneNumber) {
        try {
                String messageId =  sender.sentPhoneMessage(messageText, phoneNumber);
                MessageStatus message = new MessageStatus();
                if(messageId.length() == 34){
                    message.setMessageText(messageText);
                    message.setRecipientContact(phoneNumber);
                    message.setStatus("Delivered");
                    message.setSentMessageId(messageId);
                }else{
                    message.setMessageText(messageText);
                    message.setRecipientContact(phoneNumber);
                    message.setStatus("Not Delivered");
                }
                return message;
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }


    public MessageStatus sentMailMessage(String messageText, String email) {
        try {
                String messageId = sender.sentMailMessage(messageText, email);
                MessageStatus message = new MessageStatus();
                if(messageId.length() == 22){
                    message.setMessageText(messageText);
                    message.setRecipientContact(email);
                    message.setStatus("Delivered");
                    message.setSentMessageId(messageId);
                }else{
                    message.setMessageText(messageText);
                    message.setRecipientContact(email);
                    message.setStatus("Not Delivered");
                }
                return message;
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\+[0-9]{1,3}[0-9]{9,14}$";
        return phoneNumber.matches(phoneRegex);
    }

}
