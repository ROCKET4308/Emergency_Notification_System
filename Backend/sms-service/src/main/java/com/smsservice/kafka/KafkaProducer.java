package com.smsservice.kafka;


import com.smsservice.entity.NotificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sentNotificationStatus(NotificationStatus notificationStatus) {
        kafkaTemplate.send("rebalance-message-topic", notificationStatus);
    }

}
