package com.rebalanceservice.kafka;


import com.rebalanceservice.request.NotificationStatusRequest;
import com.rebalanceservice.service.RebalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final RebalanceService rebalanceService;

    @KafkaListener(topics = "rebalance-message-topic",groupId = "rebalance-consumer")
    public void listen(@Payload NotificationStatusRequest notificationStatusRequest) {
        rebalanceService.rebalance(notificationStatusRequest);
    }
}
