package com.api.MedSync.alert.domain.model;


import com.api.MedSync.security.domain.model.Carer;
import com.api.MedSync.security.domain.model.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Type typeAlert;

    private String message;

    private LocalDateTime date;

    private String instructions;

    @ManyToOne
    @JoinColumn(name = "carer_id", nullable = false)
    private Carer carer;



}
