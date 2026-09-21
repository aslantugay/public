package com.acme.platform.repository.query;

import java.util.Map;

/**
 * Builds the physical SQL statement used by the document search feature.
 * Implementations differ per persistence backend (native H2, ANSI, etc.).
 */
public interface DocumentQueryBuilder {

    String buildSearchSql(DocumentSearchCriteria criteria);

    Map<String, Object> describe(DocumentSearchCriteria criteria);
}
