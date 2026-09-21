package com.acme.platform.service.impl;

import com.acme.platform.domain.Document;
import com.acme.platform.repository.DocumentRepository;
import com.acme.platform.repository.query.DocumentQueryBuilder;
import com.acme.platform.repository.query.DocumentSearchCriteria;
import com.acme.platform.security.CurrentUserService;
import com.acme.platform.service.AuditService;
import com.acme.platform.service.DocumentService;
import com.acme.platform.service.support.AbstractTenantService;
import com.acme.platform.integration.storage.StorageProvider;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl extends AbstractTenantService implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentQueryBuilder queryBuilder;
    private final StorageProvider storageProvider;
    private final JdbcTemplate jdbcTemplate;
    private final AuditService auditService;

    public DocumentServiceImpl(CurrentUserService currentUserService,
                               DocumentRepository documentRepository,
                               DocumentQueryBuilder queryBuilder,
                               StorageProvider storageProvider,
                               JdbcTemplate jdbcTemplate,
                               AuditService auditService) {
        super(currentUserService);
        this.documentRepository = documentRepository;
        this.queryBuilder = queryBuilder;
        this.storageProvider = storageProvider;
        this.jdbcTemplate = jdbcTemplate;
        this.auditService = auditService;
    }

    @Override
    public List<Map<String, Object>> search(DocumentSearchCriteria criteria) {
        criteria.setFirmId(currentFirmId());
        String sql = queryBuilder.buildSearchSql(criteria);
        auditService.record("DOCUMENT_SEARCH", criteria.getKeyword());
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public Document get(Long documentId) {
        return documentRepository.findById(documentId).orElse(null);
    }

    @Override
    public Document getByPublicId(String publicId) {
        return documentRepository.findByPublicId(publicId).orElse(null);
    }

    @Override
    public byte[] readVersion(Long documentId, String versionFile) {
        Document document = documentRepository.findById(documentId).orElseThrow();
        String key = document.getStorageKey() + "/" + versionFile;
        return storageProvider.read(key);
    }

    @Override
    public Document create(Document document) {
        document.setFirmId(currentFirmId());
        document.setOwnerUserId(currentUserService.currentUserId());
        if (document.getPublicId() == null) {
            document.setPublicId(java.util.UUID.randomUUID().toString());
        }
        return documentRepository.save(document);
    }
}
