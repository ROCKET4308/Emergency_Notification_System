package com.notificationservice.service;

import com.notificationservice.entity.NotificationStatus;
import com.notificationservice.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    public Map<String, String> sentMessage(NotificationRequest notificationRequestRequest) {
        Map<String, String> deliveryStatusMap = new HashMap<>();
        List<String> contacts = notificationRequestRequest.getRecipientContacts();
        for(String contact : contacts){
            NotificationStatus notificationStatus;
            if(isEmail(contact)){
                notificationStatus = sentMailMessage(notificationRequestRequest.getMessageText(), contact);
            }
            else if(isPhoneNumber(contact)){
                notificationStatus = sentPhoneMessage(notificationRequestRequest.getMessageText(), contact);
            }
            else{
                throw new IllegalArgumentException("Not valid contact: " + contact);
            }
            deliveryStatusMap.put(notificationStatus.getRecipientContact(), notificationStatus.getStatus());
        }
        return deliveryStatusMap;
    }

    //TODO: make api request to sms service
    public NotificationStatus sentPhoneMessage(String messageText, String phoneNumber) {
        try {
                String messageId =  "";
                NotificationStatus notificationStatus = new NotificationStatus();
                if(messageId != null && messageId.length() == 34){
                    notificationStatus.setMessageText(messageText);
                    notificationStatus.setRecipientContact(phoneNumber);
                    notificationStatus.setStatus("Delivered");
                    notificationStatus.setSentMessageId(messageId);
                }else{
                    notificationStatus.setMessageText(messageText);
                    notificationStatus.setRecipientContact(phoneNumber);
                    notificationStatus.setStatus("Not Delivered");
                }
                return notificationStatus;
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    //TODO: make api request to mail service
    public NotificationStatus sentMailMessage(String messageText, String email) {
        try {
                String messageId =  "";
                NotificationStatus notificationStatus = new NotificationStatus();
                if(messageId != null && messageId.length() == 22){
                    notificationStatus.setMessageText(messageText);
                    notificationStatus.setRecipientContact(email);
                    notificationStatus.setStatus("Delivered");
                    notificationStatus.setSentMessageId(messageId);
                }else{
                    notificationStatus.setMessageText(messageText);
                    notificationStatus.setRecipientContact(email);
                    notificationStatus.setStatus("Not Delivered");
                }
                return notificationStatus;
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    public boolean isEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public boolean isPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\+[0-9]{1,3}[0-9]{9,14}$";
        return phoneNumber.matches(phoneRegex);
    }


}
