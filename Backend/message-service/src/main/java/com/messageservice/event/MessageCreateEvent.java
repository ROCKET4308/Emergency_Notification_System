package com.messageservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCreateEvent {
    private String name;
    private String messageText;
    private String recipientContacts;
}
