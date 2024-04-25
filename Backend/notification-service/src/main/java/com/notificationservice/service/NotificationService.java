package com.notificationservice.service;

import com.notificationservice.entity.NotificationStatus;
import com.notificationservice.request.MailRequest;
import com.notificationservice.request.NotificationRequest;
import com.notificationservice.request.SmsRequest;
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
            if(isEmail(contact)){
                MailRequest mailRequest = new MailRequest(notificationRequest.getMessageText(), contact);
                notificationStatus = sentMailMessage(mailRequest);
            }
            else if(isPhoneNumber(contact)){
                SmsRequest smsRequest = new SmsRequest(notificationRequest.getMessageText(), contact);
                notificationStatus = sentSmsMessage(smsRequest);
            }
            else{
                throw new IllegalArgumentException("Not valid contact: " + contact);
            }
            notificationStatus.setName(notificationRequest.getName());
            notificationStatus.setMessageText(notificationRequest.getMessageText());
            notificationStatus.setRecipientContact(contact);
            notificationStatusList.add(notificationStatus);
        }
        return notificationStatusList;
    }

    public NotificationStatus sentSmsMessage(SmsRequest smsRequest) {
        try {
                String messageId = webClientBuilder.build()
                        .post()
                        .uri("http://sms-service/sms/sent")
                        .body(Mono.just(smsRequest), SmsRequest.class)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                NotificationStatus notificationStatus = new NotificationStatus();
                if(messageId != null && messageId.length() == 34){
                    notificationStatus.setStatus("Delivered");
                    notificationStatus.setSentMessageId(messageId);
                }else{
                    notificationStatus.setStatus("Not Delivered");
                }
                return notificationStatus;
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    public NotificationStatus sentMailMessage(MailRequest mailRequest) {
        try {
                String messageId =  webClientBuilder.build()
                        .post()
                        .uri("http://mail-service/mail/sent")
                        .body(Mono.just(mailRequest), MailRequest.class)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();;

                NotificationStatus notificationStatus = new NotificationStatus();
                if(messageId != null && messageId.length() == 22){
                    notificationStatus.setStatus("Delivered");
                    notificationStatus.setSentMessageId(messageId);
                }else{
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
