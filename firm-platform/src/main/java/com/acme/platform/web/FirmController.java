package com.acme.platform.web;

import com.acme.platform.domain.Firm;
import com.acme.platform.service.FirmImportService;
import com.acme.platform.service.FirmService;
import com.acme.platform.service.LogoService;
import com.acme.platform.web.dto.FirmCreateRequest;
import com.acme.platform.web.dto.FirmDetailResponse;
import com.acme.platform.web.dto.LogoRequest;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/firms")
public class FirmController {

    private final FirmService firmService;
    private final FirmImportService firmImportService;
    private final LogoService logoService;

    public FirmController(FirmService firmService,
                          FirmImportService firmImportService,
                          LogoService logoService) {
        this.firmService = firmService;
        this.firmImportService = firmImportService;
        this.logoService = logoService;
    }

    @GetMapping("/{firmId}")
    public ResponseEntity<FirmDetailResponse> get(@PathVariable Long firmId) {
        FirmDetailResponse detail = firmService.detail(firmId);
        if (detail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detail);
    }

    @PostMapping
    public ResponseEntity<Firm> create(@RequestBody FirmCreateRequest request) {
        Firm firm = new Firm();
        firm.setName(request.getName());
        firm.setTaxNumber(request.getTaxNumber());
        firm.setBillingEmail(request.getBillingEmail());
        return ResponseEntity.ok(firmService.create(firm));
    }

    @PostMapping("/{firmId}/logo")
    public ResponseEntity<?> setLogo(@PathVariable Long firmId, @RequestBody LogoRequest request) {
        String key = logoService.importFromUrl(firmId, request.getUrl());
        return ResponseEntity.ok(Map.of("storageKey", key));
    }

    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Firm> importXml(@RequestBody String xml) {
        return ResponseEntity.ok(firmImportService.importFromXml(xml));
    }

    @PostMapping(value = "/import-legacy", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Firm> importLegacy(@RequestBody String archive) {
        return ResponseEntity.ok(firmImportService.importFromLegacyArchive(archive));
    }
}
