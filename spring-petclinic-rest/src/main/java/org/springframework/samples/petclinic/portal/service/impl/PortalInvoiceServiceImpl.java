package org.springframework.samples.petclinic.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.portal.model.Invoice;
import org.springframework.samples.petclinic.portal.repository.InvoiceRepository;
import org.springframework.samples.petclinic.portal.security.PortalContext;
import org.springframework.samples.petclinic.portal.service.PortalInvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortalInvoiceServiceImpl implements PortalInvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PortalContext portalContext;

    @Override
    public Invoice get(Integer id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    @Override
    public List<Invoice> listMine() {
        return invoiceRepository.findByOwnerAccountId(portalContext.currentAccountId());
    }

    @Override
    public Invoice update(Integer id, Invoice body) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow();
        invoice.setStatus(body.getStatus());
        invoice.setMemoHtml(body.getMemoHtml());
        invoice.setAmount(body.getAmount());
        return invoiceRepository.save(invoice);
    }
}
