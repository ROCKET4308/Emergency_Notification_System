package com.mailservice.kafka;

import com.mailservice.entity.Notification;
import com.mailservice.entity.NotificationStatus;
import com.mailservice.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final MailService mailService;
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "mail-topic",groupId = "mail-consumer")
    public void listen(@Payload Notification notification) throws IOException {
        String messageId = mailService.sentMessage(notification);
        NotificationStatus notificationStatus = new NotificationStatus();
        notificationStatus.setName(notification.getName());
        notificationStatus.setMessageText(notification.getMessageText());
        notificationStatus.setRecipientContact(notification.getRecipientContact());
        if(messageId != null && messageId.length() == 22){
            notificationStatus.setStatus("Delivered");
            notificationStatus.setSentMessageId(messageId);
        }else{
            notificationStatus.setStatus("Not Delivered");
        }
        kafkaProducer.sentNotificationStatus(notificationStatus);
        System.out.println("Sent fake message to " + notification.getRecipientContact() + " with status " + notificationStatus.getStatus());
    }
}