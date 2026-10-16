package com.helix.repo;

import com.helix.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByOwnerUserId(Long ownerUserId);
    List<Patient> findByTenantId(Long tenantId);
}
