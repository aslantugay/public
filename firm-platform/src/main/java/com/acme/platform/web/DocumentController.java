package com.acme.platform.web;

import com.acme.platform.domain.Document;
import com.acme.platform.repository.query.DocumentSearchCriteria;
import com.acme.platform.service.DocumentService;
import com.acme.platform.web.dto.DocumentCreateRequest;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/search")
    public List<Map<String, Object>> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String classification,
            @RequestParam(defaultValue = "created_at") String sort,
            @RequestParam(defaultValue = "desc") String dir) {
        DocumentSearchCriteria criteria = new DocumentSearchCriteria();
        criteria.setKeyword(q);
        criteria.setClassification(classification);
        criteria.setSortColumn(sort);
        criteria.setSortDirection(dir);
        return documentService.search(criteria);
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<Document> get(@PathVariable Long documentId) {
        Document document = documentService.get(documentId);
        return document == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(document);
    }

    @GetMapping("/shared/{publicId}")
    public ResponseEntity<Document> shared(@PathVariable String publicId) {
        Document document = documentService.getByPublicId(publicId);
        return document == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(document);
    }

    @GetMapping("/{documentId}/raw")
    public ResponseEntity<byte[]> raw(@PathVariable Long documentId,
                                      @RequestParam String file) {
        return ResponseEntity.ok(documentService.readVersion(documentId, file));
    }

    @PostMapping
    public ResponseEntity<Document> create(@RequestBody DocumentCreateRequest request) {
        Document document = new Document();
        document.setTitle(request.getTitle());
        document.setClassification(request.getClassification());
        document.setNotes(request.getNotes());
        document.setStorageKey(request.getStorageKey());
        document.setContentType(request.getContentType());
        document.setUnitId(request.getUnitId());
        return ResponseEntity.ok(documentService.create(document));
    }
}
