package com.meridian.dataio.web;

import com.meridian.dataio.TenantImportService;
import com.meridian.tenancy.domain.Tenant;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportController implements ImportApi {

    private final TenantImportService tenantImportService;

    public ImportController(TenantImportService tenantImportService) {
        this.tenantImportService = tenantImportService;
    }

    @Override
    public Tenant importXml(String xml) {
        return tenantImportService.importXml(xml);
    }

    @Override
    public Tenant importLegacy(String archive) {
        return tenantImportService.importLegacyArchive(archive);
    }

    @Override
    public List<String> importAttachments(Map<String, String> body) {
        return tenantImportService.importAttachmentBundle(body.get("targetDir"), body.get("zip"));
    }
}
