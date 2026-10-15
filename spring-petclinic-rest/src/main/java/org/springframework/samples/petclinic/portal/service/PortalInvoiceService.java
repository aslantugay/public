package org.springframework.samples.petclinic.portal.service;

import org.springframework.samples.petclinic.portal.model.Invoice;

import java.util.List;

public interface PortalInvoiceService {

    Invoice get(Integer id);

    List<Invoice> listMine();

    Invoice update(Integer id, Invoice body);
}
