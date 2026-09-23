package com.meridian.docs.facade;

import com.meridian.docs.query.DocumentSearchCriteria;
import com.meridian.docs.service.DocumentService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DocumentFacadeImpl implements DocumentFacade {

    private final DocumentService documentService;

    public DocumentFacadeImpl(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public List<Map<String, Object>> search(DocumentSearchCriteria criteria) {
        return documentService.search(criteria);
    }
}
