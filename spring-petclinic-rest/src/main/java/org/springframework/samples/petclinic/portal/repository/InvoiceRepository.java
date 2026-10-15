package org.springframework.samples.petclinic.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.portal.model.Invoice;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByOwnerAccountId(Integer ownerAccountId);
}
