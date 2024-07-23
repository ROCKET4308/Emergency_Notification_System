package com.rebalanceservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationStatusRequest {
    private String name;
    private String messageText;
    private String recipientContact;
    private String status;
    private String sentMessageId;
}
