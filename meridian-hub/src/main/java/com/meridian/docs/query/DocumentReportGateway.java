package com.meridian.docs.query;

import java.util.List;
import java.util.Map;

public interface DocumentReportGateway {

    List<Map<String, Object>> runSavedFilter(Long tenantId, String whereClause);
}
