package com.rebalanceservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private String name;
    private String messageText;
    private List<String> recipientContacts;
}