package org.springframework.samples.petclinic.portal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.portal.model.Invoice;
import org.springframework.samples.petclinic.portal.service.PortalInvoiceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/portal/invoices")
public class PortalInvoiceController {

    @Autowired
    private PortalInvoiceService invoiceService;

    @GetMapping("/mine")
    public List<Invoice> mine() {
        return invoiceService.listMine();
    }

    @GetMapping("/{id}")
    public Invoice get(@PathVariable Integer id) {
        return invoiceService.get(id);
    }

    @PutMapping("/{id}")
    public Invoice update(@PathVariable Integer id, @RequestBody Invoice body) {
        return invoiceService.update(id, body);
    }
}
