package com.meridian.docs.service;

import com.meridian.docs.domain.Document;
import com.meridian.docs.query.DocumentSearchCriteria;
import java.util.List;
import java.util.Map;

public interface DocumentService {

    List<Map<String, Object>> search(DocumentSearchCriteria criteria);

    Document get(Long documentId);

    Document getByShareToken(String shareToken);

    byte[] readVersion(Long documentId, String versionFile);

    Document create(Document document);
}
