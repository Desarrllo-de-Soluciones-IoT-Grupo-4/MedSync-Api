package com.api.MedSync.security.domain.model;

import com.api.MedSync.monitoring.domain.model.HeartRate;
import com.api.MedSync.monitoring.domain.model.Metric;
import com.api.MedSync.security.domain.aggregate.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("PATIENT")
@Table(name = "patients")
public class Patient extends User {

    private String disease;

    private Double weight;

    @JsonIgnore
    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private Carer carer;

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HeartRate> heartRates;

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Metric> metrics;



}
