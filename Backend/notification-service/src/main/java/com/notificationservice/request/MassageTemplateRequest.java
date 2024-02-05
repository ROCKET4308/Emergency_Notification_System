package com.notificationservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MassageTemplateRequest {
    private String templateName;
    private String massageText;
    private List<String> contacts;
}
