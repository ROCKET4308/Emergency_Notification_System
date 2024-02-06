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
public class MessageTemplate {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    private User userId;
    private String templateName;
    private String recipientContact;
    private String messageText;

}
