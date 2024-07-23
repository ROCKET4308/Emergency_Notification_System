package com.messageservice.kafka;

import com.messageservice.request.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, MessageRequest> kafkaTemplate;

    public void sentMessage(MessageRequest messageRequest) {
        kafkaTemplate.send("sent-message-topic", messageRequest);
    }

}
