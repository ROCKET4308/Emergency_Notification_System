package com.notificationservice.service;

import com.notificationservice.entity.Notification;
import com.notificationservice.kafka.KafkaProducer;
import com.notificationservice.request.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final KafkaProducer kafkaProducer;

    public void sentMessage(MessageRequest messageRequest) {
        List<String> contacts = messageRequest.getRecipientContacts();
        for(String contact : contacts){
            Notification notification = new Notification(messageRequest.getName(), messageRequest.getMessageText(), contact);
            if (isFakeMessage(contact)) {
                kafkaProducer.sentFake(notification);
            }
            else if(isPhoneNumber(contact)){
                kafkaProducer.sentSms(notification);
            } else if(isEmail(contact)){
                kafkaProducer.sentMail(notification);
            }else{
                throw new IllegalArgumentException("Not valid contact: " + contact);
            }
        }
    }

    private boolean isFakeMessage(String contact) {
        String fakeMail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@fake.com";
        return contact.matches(fakeMail);
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
