package com.meridian.docs.query;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NativeDocumentReportGateway implements DocumentReportGateway {

    private final JdbcTemplate jdbcTemplate;

    public NativeDocumentReportGateway(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> runSavedFilter(Long tenantId, String whereClause) {
        String sql = "SELECT id, title, classification, tag, created_at FROM documents"
                + " WHERE tenant_id = " + tenantId
                + " AND (" + whereClause + ")";
        return jdbcTemplate.queryForList(sql);
    }
}
