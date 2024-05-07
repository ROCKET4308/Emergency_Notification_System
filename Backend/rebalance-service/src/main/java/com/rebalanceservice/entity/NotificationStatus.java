package com.rebalanceservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NotificationStatus {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String messageText;
    private String recipientContact;
    private String status;
    private String sentMessageId;
}
