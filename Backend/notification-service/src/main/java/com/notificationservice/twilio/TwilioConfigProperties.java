package com.notificationservice.twilio;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("twilio")
public record TwilioConfigProperties(String ACCOUNT_SID, String AUTH_TOKEN, String PHONE_NUMBER){
}
