package com.fakemessageservice.service;

import com.fakemessageservice.request.FakeMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FakeMessageService {
    public String sentMessage(FakeMessageRequest fakeMessageRequest) {
        return sentFakeMessage(fakeMessageRequest.getMessageText(), fakeMessageRequest.getRecipientContact());
    }

    public String sentFakeMessage(String messageText, String recipientNumber){
        String messageId = "fakemessageid";
        System.out.println("Message ID: " + messageId);
        return messageId;
    }

}
