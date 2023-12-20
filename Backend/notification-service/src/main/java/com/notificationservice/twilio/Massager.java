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
        Twilio.init(twilioConfigProperties.ACCOUNT_SID(), twilioConfigProperties.AUTH_TOKEN());
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(recipientNumber),
                        new com.twilio.type.PhoneNumber(twilioConfigProperties.PHONE_NUMBER()),
                        massageText)
                .create();

        System.out.println(message.getSid());
    }
}