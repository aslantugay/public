package com.meridian.docs.web;

import com.meridian.docs.domain.Document;
import com.meridian.docs.dto.DocumentCreateRequest;
import com.meridian.docs.facade.DocumentFacade;
import com.meridian.docs.query.DocumentSearchCriteria;
import com.meridian.docs.service.DocumentService;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController implements DocumentApi {

    private final DocumentFacade documentFacade;
    private final DocumentService documentService;

    public DocumentController(DocumentFacade documentFacade, DocumentService documentService) {
        this.documentFacade = documentFacade;
        this.documentService = documentService;
    }

    @Override
    public List<Map<String, Object>> search(String q, String classification, String sort, String dir) {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setKeyword(q);
        criteria.setClassification(classification);
        criteria.setSortColumn(sort);
        criteria.setSortDirection(dir);
        return documentFacade.search(criteria);
    }

    @Override
    public ResponseEntity<Document> get(Long documentId) {
        Document document = documentService.get(documentId);
        return document == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(document);
    }

    @Override
    public ResponseEntity<Document> shared(String shareToken) {
        Document document = documentService.getByShareToken(shareToken);
        return document == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(document);
    }

    @Override
    public ResponseEntity<byte[]> raw(Long documentId, String file) {
        return ResponseEntity.ok(documentService.readVersion(documentId, file));
    }

    @Override
    public ResponseEntity<Document> create(DocumentCreateRequest request) {
        Document document = new Document();
        document.setTitle(request.getTitle());
        document.setClassification(request.getClassification());
        document.setNotes(request.getNotes());
        document.setStorageKey(request.getStorageKey());
        document.setContentType(request.getContentType());
        document.setTag(request.getTag());
        document.setUnitId(request.getUnitId());
        return ResponseEntity.ok(documentService.create(document));
    }
}
