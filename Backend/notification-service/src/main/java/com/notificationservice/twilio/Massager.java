package com.notificationservice.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Massager {

    @Value("${application.security.account_sid}")
    private static String ACCOUNT_SID;

    @Value("${application.security.auth_token}")
    private static String AUTH_TOKEN;

    @Value("${application.security.phone_number}")
    private static String PHONE_NUMBER;

    public static void sentPhoneMassage(String massageText, String recipientNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(recipientNumber),
                        new com.twilio.type.PhoneNumber(PHONE_NUMBER),
                        massageText)
                .create();

        System.out.println(message.getSid());
    }
}