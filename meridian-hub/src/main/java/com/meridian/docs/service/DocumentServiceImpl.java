package com.meridian.docs.service;

import com.meridian.docs.DocumentRepository;
import com.meridian.docs.domain.Document;
import com.meridian.docs.query.DocumentQueryGateway;
import com.meridian.docs.query.DocumentSearchCriteria;
import com.meridian.integration.storage.BlobStore;
import com.meridian.platform.audit.AuditService;
import com.meridian.platform.security.AbstractTenantService;
import com.meridian.platform.security.CurrentUserService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl extends AbstractTenantService implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentQueryGateway queryGateway;
    private final BlobStore blobStore;
    private final AuditService auditService;

    public DocumentServiceImpl(CurrentUserService currentUserService,
                               DocumentRepository documentRepository,
                               DocumentQueryGateway queryGateway,
                               BlobStore blobStore,
                               AuditService auditService) {
        super(currentUserService);
        this.documentRepository = documentRepository;
        this.queryGateway = queryGateway;
        this.blobStore = blobStore;
        this.auditService = auditService;
    }

    @Override
    public List<Map<String, Object>> search(DocumentSearchCriteria criteria) {
        criteria.setTenantId(currentTenantId());
        auditService.record("DOCUMENT_SEARCH", criteria.getKeyword());
        return queryGateway.search(criteria);
    }

    @Override
    public Document get(Long documentId) {
        return documentRepository.findById(documentId).orElse(null);
    }

    @Override
    public Document getByShareToken(String shareToken) {
        return documentRepository.findByShareToken(shareToken).orElse(null);
    }

    @Override
    public byte[] readVersion(Long documentId, String versionFile) {
        Document document = documentRepository.findById(documentId).orElseThrow();
        String key = document.getStorageKey() + "/" + versionFile;
        return blobStore.read(key);
    }

    @Override
    public Document create(Document document) {
        document.setTenantId(currentTenantId());
        document.setOwnerUserId(currentUserId());
        if (document.getShareToken() == null) {
            document.setShareToken(UUID.randomUUID().toString());
        }
        return documentRepository.save(document);
    }
}
