package com.notificationservice.kafka;


import com.notificationservice.request.MessageRequest;
import com.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final NotificationService messageService;

    @KafkaListener(topics = "sent-message-topic",groupId = "notification-consumer")
    public void listen(@Payload MessageRequest messageRequest) {
        messageService.sentMessage(messageRequest);
    }
}
