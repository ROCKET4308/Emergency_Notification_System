package com.notificationservice.service;

import com.notificationservice.entity.MessageStatus;
import com.notificationservice.repository.MessageStatusRepository;
import com.notificationservice.request.MessageRequest;
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

    public Map<String, String> sentMessage(MessageRequest messageRequest) {
        Map<String, String> deliveryStatusMap = new HashMap<>();
        List<String> contacts = messageRequest.getContacts();
        for(String contact : contacts){
            if(isValidEmail(contact)){
                deliveryStatusMap.putAll(sentMailMessage(messageRequest.getMessageText(), contact));
            }
            else if(isValidPhoneNumber(contact)){
                deliveryStatusMap.putAll(sentPhoneMessage(messageRequest.getMessageText(), contact));
            }
            else{
                throw new IllegalArgumentException("Not valid contact: " + contact);
            }
        }
        return deliveryStatusMap;
    }


    public Map<String, String> sentPhoneMessage(String messageText, String phoneNumber) {
        Map<String, String> deliveryStatusMap = new HashMap<>();
        try {
                String messageId =  sender.sentPhoneMessage(messageText, phoneNumber);
                MessageStatus message = new MessageStatus();
                if(messageId.length() == 34){
                    message.setMessageText(messageText);
                    message.setRecipientContact(phoneNumber);
                    message.setStatus("Delivered");
                    message.setSentMessageId(messageId);
                     deliveryStatusMap.put(phoneNumber, "Delivered");
                }else{
                    message.setMessageText(messageText);
                    message.setRecipientContact(phoneNumber);
                    message.setStatus("Not Delivered");
                    deliveryStatusMap.put(phoneNumber, "Not Delivered");
                }
            messageStatusRepository.save(message);
        }catch (Exception e){
            System.out.println(e);
        }
        return deliveryStatusMap;
    }

    public Map<String, String> sentMailMessage(String messageText, String email) {
        Map<String, String> deliveryStatusMap = new HashMap<>();
        try {
                String messageId = sender.sentMailMessage(messageText, email);
                MessageStatus message = new MessageStatus();
                if(messageId.length() == 22){
                    message.setMessageText(messageText);
                    message.setRecipientContact(email);
                    message.setStatus("Delivered");
                    message.setSentMessageId(messageId);
                    deliveryStatusMap.put(email, "Delivered");
                }else{
                    message.setMessageText(messageText);
                    message.setRecipientContact(email);
                    message.setStatus("Not Delivered");
                    deliveryStatusMap.put(email, "Not Delivered");
                }
                messageStatusRepository.save(message);
        }catch (Exception e){
            System.out.println(e);
        }
        return deliveryStatusMap;
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
