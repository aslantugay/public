package com.acme.platform.service;

import com.acme.platform.domain.Firm;

public interface FirmImportService {

    Firm importFromXml(String xml);

    Firm importFromLegacyArchive(String archive);
}
