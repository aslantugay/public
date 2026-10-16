package com.helix.invoice;

import com.helix.common.CommonResult;
import com.helix.model.Invoice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public CommonResult get(@PathVariable Long id) {
        return CommonResult.success(service.get(id));
    }

    @PostMapping("/update")
    public CommonResult update(@RequestBody Invoice invoice) {
        return CommonResult.success(service.update(invoice));
    }

    @GetMapping("/report")
    public CommonResult report(@RequestParam String tenant) {
        return CommonResult.success(service.revenueReport(tenant));
    }

    @GetMapping("/{id}/export")
    public CommonResult export(@PathVariable Long id, @RequestParam String name) {
        return CommonResult.success(service.exportPdf(id, name));
    }
}
