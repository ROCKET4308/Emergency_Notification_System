package com.notificationservice.service;

import com.notificationservice.entity.NotificationStatus;
import com.notificationservice.request.MessageRequest;
import com.notificationservice.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final WebClient.Builder webClientBuilder;

    public List<NotificationStatus> sentMessage(NotificationRequest notificationRequest) {
        List<NotificationStatus> notificationStatusList = new ArrayList<>();
        List<String> contacts = notificationRequest.getRecipientContacts();
        for(String contact : contacts){
            NotificationStatus notificationStatus;
            MessageRequest messageRequest = new MessageRequest(notificationRequest.getMessageText(), contact);
            if(isEmail(contact)){
                notificationStatus = sentMailMessage(messageRequest);
            }
            else if(isPhoneNumber(contact)){
                notificationStatus = sentSmsMessage(messageRequest);
            }
            else{
                throw new IllegalArgumentException("Not valid contact: " + contact);
            }
            notificationStatusList.add(notificationStatus);
        }
        return notificationStatusList;
    }

    public NotificationStatus sentSmsMessage(MessageRequest messageRequest) {
        try {
                String messageId = webClientBuilder.build()
                        .post()
                        .uri("http://sms-service/sms/sent")
                        .body(Mono.just(messageRequest), MessageRequest.class)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                NotificationStatus notificationStatus = new NotificationStatus();
                if(messageId != null && messageId.length() == 34){
                    notificationStatus.setMessageText(messageRequest.getMessageText());
                    notificationStatus.setRecipientContact(messageRequest.getRecipientContact());
                    notificationStatus.setStatus("Delivered");
                    notificationStatus.setSentMessageId(messageId);
                }else{
                    notificationStatus.setMessageText(messageRequest.getMessageText());
                    notificationStatus.setRecipientContact(messageRequest.getRecipientContact());
                    notificationStatus.setStatus("Not Delivered");
                }
                return notificationStatus;
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    public NotificationStatus sentMailMessage(MessageRequest messageRequest) {
        try {
                String messageId =  webClientBuilder.build()
                        .post()
                        .uri("http://mail-service/mail/sent")
                        .body(Mono.just(messageRequest), MessageRequest.class)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();;

                NotificationStatus notificationStatus = new NotificationStatus();
                if(messageId != null && messageId.length() == 22){
                    notificationStatus.setMessageText(messageRequest.getMessageText());
                    notificationStatus.setRecipientContact(messageRequest.getRecipientContact());
                    notificationStatus.setStatus("Delivered");
                    notificationStatus.setSentMessageId(messageId);
                }else{
                    notificationStatus.setMessageText(messageRequest.getMessageText());
                    notificationStatus.setRecipientContact(messageRequest.getRecipientContact());
                    notificationStatus.setStatus("Not Delivered");
                }
                return notificationStatus;
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    public boolean isEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public boolean isPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\+[0-9]{1,3}[0-9]{9,14}$";
        return phoneNumber.matches(phoneRegex);
    }


}
