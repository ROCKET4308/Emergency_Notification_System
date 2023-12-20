package com.notificationservice.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class Massager {
    private final TwilioConfigProperties twilioConfigProperties;

    public void sentPhoneMassage(String massageText, String recipientNumber) {
        Twilio.init(twilioConfigProperties.account_sid(), twilioConfigProperties.auth_token());
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(recipientNumber),
                        new com.twilio.type.PhoneNumber(twilioConfigProperties.phone_number()),
                        massageText)
                .create();
        System.out.println(message.getSid());
    }
}