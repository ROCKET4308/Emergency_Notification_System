package com.fakemessageservice.service;

import com.fakemessageservice.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FakeMessageService {

    public String sentMessage(Notification notification) {
        return sentFakeMessage(notification.getMessageText(), notification.getRecipientContact());
    }

    public String sentFakeMessage(String messageText, String recipientNumber){
        String messageId = "fakemessageid";
        System.out.println("Message ID: " + messageId);
        return messageId;
    }

}
