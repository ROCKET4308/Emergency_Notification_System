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
    @ManyToOne
    private Mailing mailing;
    private String userContact;
    private String status;
    private String sentMassageId;
}
