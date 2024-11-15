package com.api.MedSync.security.domain.model;

import com.api.MedSync.alert.domain.model.Alert;
import com.api.MedSync.monitoring.domain.model.HeartRate;
import com.api.MedSync.security.domain.aggregate.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("CARER")
@Table(name = "carers")
public class Carer extends User {

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    private String relationship;

    @JsonIgnore
    @OneToMany(mappedBy = "carer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alert> alerts;



}
