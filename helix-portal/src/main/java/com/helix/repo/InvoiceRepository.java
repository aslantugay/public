package com.helix.repo;

import com.helix.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByOwnerUserId(Long ownerUserId);
    List<Invoice> findByPatientId(Long patientId);
}
