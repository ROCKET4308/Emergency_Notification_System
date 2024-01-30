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
public class Massage {
    @Id
    @GeneratedValue
    private  Integer id;
    private String messageText;
    private String recipientContact;
    private String status;
    private String sentMassageId;
}
