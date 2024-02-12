package com.notificationservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageTemplateResponse {
    private String templateName;
    private String messageText;
    private List<String> recipientContacts;
}
