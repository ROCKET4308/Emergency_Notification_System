package com.smsservice.kafka;

import com.smsservice.entity.Notification;
import com.smsservice.entity.NotificationStatus;
import com.smsservice.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final SmsService smsService;
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "sms-topic",groupId = "sms-consumer")
    public void listen(@Payload Notification notification) {
        String messageId = smsService.sentMessage(notification);
        NotificationStatus notificationStatus = new NotificationStatus();
        notificationStatus.setName(notification.getName());
        notificationStatus.setMessageText(notification.getMessageText());
        notificationStatus.setRecipientContact(notification.getRecipientContact());
        if(messageId != null && messageId.length() == 13){
            notificationStatus.setStatus("Delivered");
            notificationStatus.setSentMessageId(messageId);
        }else{
            notificationStatus.setStatus("Not Delivered");
        }
        System.out.println("Sent fake message to " + notificationStatus.getRecipientContact() + " with status " + notificationStatus.getStatus());
        kafkaProducer.sentNotificationStatus(notificationStatus);
    }
}
