package com.fakemessageservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationStatus {
    private String name;
    private String messageText;
    private String recipientContact;
    private String status;
    private String sentMessageId;
    private Integer retrySentCounter;
    private LocalDateTime messageSentTime;
}
