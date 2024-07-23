package com.smsservice.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.smsservice.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {
    @Value("${twilio.account_sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth_token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone_number}")
    private String PHONE_NUMBER;

    public String sentMessage(Notification notification) {
        return sentSms(notification.getMessageText(), notification.getRecipientContact());
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
        return messageId;
    }

}
