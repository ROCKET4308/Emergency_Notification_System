package com.rebalanceservice.service;

import com.rebalanceservice.entity.NotificationStatus;
import com.rebalanceservice.kafka.KafkaProducer;
import com.rebalanceservice.repository.NotificationStatusRepository;
import com.rebalanceservice.request.MessageRequest;
import com.rebalanceservice.request.NotificationStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RebalanceService {
    private final NotificationStatusRepository notificationStatusRepository;
    private final KafkaProducer kafkaProducer;

    public void rebalance(NotificationStatusRequest notificationStatusRequest) {
        if(notificationStatusRequest.getStatus().equals("Delivered")){
            if(notificationStatusRepository.findByNameAndRecipientContact(notificationStatusRequest.getName(), notificationStatusRequest.getRecipientContact()).isPresent()) {
                Optional<NotificationStatus> notificationStatus = notificationStatusRepository.findByNameAndRecipientContact(notificationStatusRequest.getName(), notificationStatusRequest.getRecipientContact());
                notificationStatus.get().setStatus(notificationStatusRequest.getStatus());
                notificationStatus.get().setMessageSentTime(LocalDateTime.now());
                notificationStatusRepository.save(notificationStatus.get());
            }else {
                NotificationStatus notificationStatus = new NotificationStatus();
                notificationStatus.setName(notificationStatusRequest.getName());
                notificationStatus.setMessageText(notificationStatusRequest.getMessageText());
                notificationStatus.setRecipientContact(notificationStatusRequest.getRecipientContact());
                notificationStatus.setStatus(notificationStatusRequest.getStatus());
                notificationStatus.setSentMessageId(notificationStatusRequest.getSentMessageId());
                notificationStatus.setMessageSentTime(LocalDateTime.now());
                notificationStatusRepository.save(notificationStatus);
            }
        }
        else if(notificationStatusRequest.getStatus().equals("Not Delivered")){
            if(notificationStatusRepository.findByNameAndRecipientContact(notificationStatusRequest.getName(), notificationStatusRequest.getRecipientContact()).isEmpty()){
                NotificationStatus notificationStatus = new NotificationStatus();
                notificationStatus.setName(notificationStatusRequest.getName());
                notificationStatus.setMessageText(notificationStatusRequest.getMessageText());
                notificationStatus.setRecipientContact(notificationStatusRequest.getRecipientContact());
                notificationStatus.setStatus(notificationStatusRequest.getStatus());
                notificationStatus.setSentMessageId(notificationStatusRequest.getSentMessageId());
                notificationStatus.setRetrySentCounter(1);
                notificationStatus.setMessageSentTime(LocalDateTime.now());
                notificationStatusRepository.save(notificationStatus);
                MessageRequest messageRequest = new MessageRequest();
                messageRequest.setName(notificationStatusRequest.getName());
                messageRequest.setMessageText(notificationStatusRequest.getMessageText());
                List<String> contacts = new ArrayList<>();
                contacts.add(notificationStatusRequest.getRecipientContact());
                messageRequest.setRecipientContacts(contacts);
                kafkaProducer.sentMessage(messageRequest);
            } else if(notificationStatusRepository.findByNameAndRecipientContact(notificationStatusRequest.getName(), notificationStatusRequest.getRecipientContact()).isPresent()) {
                Optional<NotificationStatus> notificationStatusOptional = notificationStatusRepository.findByNameAndRecipientContact(notificationStatusRequest.getName(), notificationStatusRequest.getRecipientContact());
                if (notificationStatusOptional.isPresent()) {
                    NotificationStatus notificationStatus = notificationStatusOptional.get();
                    Integer counter = notificationStatus.getRetrySentCounter();
                    counter += 1;
                    notificationStatus.setRetrySentCounter(counter);
                    notificationStatusRepository.save(notificationStatus);
                    if (counter <= 3) {
                        MessageRequest messageRequest = new MessageRequest();
                        messageRequest.setName(notificationStatusRequest.getName());
                        messageRequest.setMessageText(notificationStatusRequest.getMessageText());
                        List<String> contacts = new ArrayList<>();
                        contacts.add(notificationStatusRequest.getRecipientContact());
                        messageRequest.setRecipientContacts(contacts);
                        kafkaProducer.sentMessage(messageRequest);
                    }
                }
            }

        }

    }
}