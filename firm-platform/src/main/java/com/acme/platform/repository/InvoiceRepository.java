package com.acme.platform.repository;

import com.acme.platform.domain.Invoice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByFirmId(Long firmId);
}
