package com.acme.platform.service.impl;

import com.acme.platform.domain.Firm;
import com.acme.platform.integration.xml.XmlFirmImporter;
import com.acme.platform.repository.FirmRepository;
import com.acme.platform.service.FirmImportService;
import com.thoughtworks.xstream.XStream;
import org.springframework.stereotype.Service;

@Service
public class FirmImportServiceImpl implements FirmImportService {

    private final XmlFirmImporter xmlFirmImporter;
    private final FirmRepository firmRepository;

    public FirmImportServiceImpl(XmlFirmImporter xmlFirmImporter, FirmRepository firmRepository) {
        this.xmlFirmImporter = xmlFirmImporter;
        this.firmRepository = firmRepository;
    }

    @Override
    public Firm importFromXml(String xml) {
        Firm firm = xmlFirmImporter.parse(xml);
        return firmRepository.save(firm);
    }

    @Override
    public Firm importFromLegacyArchive(String archive) {
        XStream xstream = new XStream();
        Firm firm = (Firm) xstream.fromXML(archive);
        return firmRepository.save(firm);
    }
}
