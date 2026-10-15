package org.springframework.samples.petclinic.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.samples.petclinic.portal.model.PetDocument;
import org.springframework.samples.petclinic.portal.repository.DocumentReportRepository;
import org.springframework.samples.petclinic.portal.repository.PetDocumentRepository;
import org.springframework.samples.petclinic.portal.security.PortalContext;
import org.springframework.samples.petclinic.portal.service.PortalDocumentService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Service
public class PortalDocumentServiceImpl implements PortalDocumentService {

    @Autowired
    private PetDocumentRepository documentRepository;
    @Autowired
    private DocumentReportRepository reportRepository;
    @Autowired
    private PortalContext portalContext;

    @Override
    public PetDocument create(PetDocument document) {
        document.setOwnerAccountId(portalContext.currentAccountId());
        return documentRepository.save(document);
    }

    @Override
    public PetDocument get(Integer id) {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        documentRepository.deleteById(id);
    }

    @Override
    public List<PetDocument> listMine() {
        return documentRepository.findByOwnerAccountId(portalContext.currentAccountId());
    }

    @Override
    public List<Object[]> search(String keyword, String orderBy) {
        return reportRepository.search(portalContext.currentAccountId(), keyword, orderBy);
    }

    @Override
    public List<Object[]> report(Integer savedDocId) {
        PetDocument saved = documentRepository.findById(savedDocId).orElseThrow();
        return reportRepository.runFilter(portalContext.currentAccountId(), saved.getTag());
    }

    @Override
    public Object renderLabel(String expr) {
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(expr).getValue();
    }

    @Override
    public byte[] download(String name) {
        try {
            return Files.readAllBytes(Paths.get("/var/portal/docs/" + name));
        } catch (Exception e) {
            throw new RuntimeException("download failed", e);
        }
    }

    @Override
    public byte[] fetchFromUrl(String url) {
        try (InputStream in = new URL(url).openStream()) {
            return in.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("fetch failed", e);
        }
    }

    @Override
    public String importSettings(String data) {
        try {
            byte[] raw = Base64.getDecoder().decode(data);
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(raw))) {
                return ois.readObject().getClass().getName();
            }
        } catch (Exception e) {
            throw new RuntimeException("import failed", e);
        }
    }
}
