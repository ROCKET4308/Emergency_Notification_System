package com.notificationservice.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Massager {

    @Value("${twilio.account_sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth_token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone_number}")
    private String PHONE_NUMBER;

    public void sentPhoneMassage(String massageText, String recipientNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(recipientNumber),
                        new com.twilio.type.PhoneNumber(PHONE_NUMBER),
                        massageText)
                .create();
        System.out.println(message.getSid());
    }
}