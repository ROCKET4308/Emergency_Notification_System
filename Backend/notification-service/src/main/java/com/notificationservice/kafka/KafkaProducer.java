package com.notificationservice.kafka;

import com.notificationservice.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sentSms(Notification notification) {
        kafkaTemplate.send("sms-topic", notification);
    }

    public void sentMail(Notification notification) {
        kafkaTemplate.send("mail-topic", notification);
    }

    public void sentFake(Notification notification) {
        kafkaTemplate.send("fake-topic", notification);
    }

}
