package com.notificationservice.twilio;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("twilio")
public record TwilioConfigProperties(String account_sid, String auth_token, String phone_number){
}
