package com.api.MedSync.security.domain.model;

import com.api.MedSync.security.domain.aggregate.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("CARER")
@Table(name = "carers")
public class Carer extends User {

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

}
