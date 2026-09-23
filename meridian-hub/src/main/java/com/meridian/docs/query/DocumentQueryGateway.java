package com.meridian.docs.query;

import java.util.List;
import java.util.Map;

/**
 * Executes the physical document search. Implementations vary per persistence
 * backend (native H2, ANSI, search index).
 */
public interface DocumentQueryGateway {

    List<Map<String, Object>> search(DocumentSearchCriteria criteria);
}
