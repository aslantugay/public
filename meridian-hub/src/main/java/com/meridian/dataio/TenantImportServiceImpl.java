package com.meridian.dataio;

import com.meridian.integration.storage.BlobStore;
import com.meridian.integration.xml.XmlImportParser;
import com.meridian.tenancy.TenantRepository;
import com.meridian.tenancy.domain.Tenant;
import com.thoughtworks.xstream.XStream;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

@Service
public class TenantImportServiceImpl implements TenantImportService {

    private final XmlImportParser xmlImportParser;
    private final TenantRepository tenantRepository;
    private final BlobStore blobStore;

    public TenantImportServiceImpl(XmlImportParser xmlImportParser,
                                   TenantRepository tenantRepository,
                                   BlobStore blobStore) {
        this.xmlImportParser = xmlImportParser;
        this.tenantRepository = tenantRepository;
        this.blobStore = blobStore;
    }

    @Override
    public Tenant importXml(String xml) {
        Document doc = xmlImportParser.parse(xml);
        Tenant tenant = new Tenant();
        tenant.setName(text(doc, "name"));
        tenant.setTaxNumber(text(doc, "taxNumber"));
        tenant.setBillingEmail(text(doc, "billingEmail"));
        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant importLegacyArchive(String archive) {
        XStream xstream = new XStream();
        Tenant tenant = (Tenant) xstream.fromXML(archive);
        return tenantRepository.save(tenant);
    }

    @Override
    public List<String> importAttachmentBundle(String targetDir, String base64Zip) {
        byte[] zip = Base64.getDecoder().decode(base64Zip);
        return blobStore.extractArchive(targetDir, zip);
    }

    private String text(Document doc, String tag) {
        NodeList nodes = doc.getElementsByTagName(tag);
        return nodes.getLength() == 0 ? null : nodes.item(0).getTextContent();
    }
}
