package com.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MessageStatus {
    @Id
    @GeneratedValue
    private  Integer id;
    @ManyToOne
    private User user;
    private String templateName;
    private String messageText;
    private String recipientContact;
    private String status;
    private String sentMessageId;
}
