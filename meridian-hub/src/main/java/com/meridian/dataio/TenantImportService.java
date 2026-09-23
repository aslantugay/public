package com.meridian.dataio;

import com.meridian.tenancy.domain.Tenant;
import java.util.List;

public interface TenantImportService {

    Tenant importXml(String xml);

    Tenant importLegacyArchive(String archive);

    List<String> importAttachmentBundle(String targetDir, String base64Zip);
}
