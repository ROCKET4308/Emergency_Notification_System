package com.smsservice.service;

import com.smsservice.request.MessageRequest;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SmsService {
    @Value("${twilio.account_sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth_token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone_number}")
    private String PHONE_NUMBER;

    public Map<String, String> sentMessage(MessageRequest messageRequest) {
        Map<String, String> deliveryStatusMap = new HashMap<>();
        List<String> contacts = messageRequest.getRecipientContacts();
        for(String contact : contacts){
            String status;
            if(isValidPhoneNumber(contact)){
                status = sentSms(messageRequest.getMessageText(), contact);
            }
            else{
                throw new IllegalArgumentException("Not valid contact: " + contact);
            }
            deliveryStatusMap.put(contact, status);
        }
        return deliveryStatusMap;
    }

    public String sentSms(String messageText, String recipientNumber){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(recipientNumber),
                        new com.twilio.type.PhoneNumber(PHONE_NUMBER),
                        messageText)
                .create();
        String messageId = message.getSid();
        System.out.println("Message ID: " + messageId);
        String status;
        if(messageId != null && messageId.length() == 34){
            status = "Delivered";
        }else{
            status = "Not Delivered";
        }
        return status;
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\+[0-9]{1,3}[0-9]{9,14}$";
        return phoneNumber.matches(phoneRegex);
    }
}
