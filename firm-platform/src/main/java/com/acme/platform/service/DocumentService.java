package com.acme.platform.service;

import com.acme.platform.domain.Document;
import com.acme.platform.repository.query.DocumentSearchCriteria;
import java.util.List;
import java.util.Map;

public interface DocumentService {

    List<Map<String, Object>> search(DocumentSearchCriteria criteria);

    Document get(Long documentId);

    Document getByPublicId(String publicId);

    byte[] readVersion(Long documentId, String versionFile);

    Document create(Document document);
}
