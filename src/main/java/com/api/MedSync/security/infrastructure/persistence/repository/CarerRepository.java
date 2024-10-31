package com.api.MedSync.security.infrastructure.persistence.repository;

import com.api.MedSync.security.domain.model.Carer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarerRepository extends JpaRepository<Carer, Long> {
    Carer findByPhoneNumber(String phoneNumber);

}
