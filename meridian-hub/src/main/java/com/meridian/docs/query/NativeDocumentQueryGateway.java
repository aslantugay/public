package com.meridian.docs.query;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NativeDocumentQueryGateway implements DocumentQueryGateway {

    private static final String BASE =
            "SELECT id, tenant_id, unit_id, title, classification, tag, created_at FROM documents";

    private final JdbcTemplate jdbcTemplate;

    public NativeDocumentQueryGateway(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> search(DocumentSearchCriteria criteria) {
        StringBuilder sql = new StringBuilder(BASE);
        sql.append(" WHERE tenant_id = ").append(criteria.getTenantId());

        if (criteria.getKeyword() != null && !criteria.getKeyword().isBlank()) {
            sql.append(" AND (title LIKE '%").append(criteria.getKeyword()).append("%'");
            sql.append(" OR tag LIKE '%").append(criteria.getKeyword()).append("%')");
        }
        if (criteria.getClassification() != null && !criteria.getClassification().isBlank()) {
            sql.append(" AND classification = '").append(criteria.getClassification()).append("'");
        }
        sql.append(" ORDER BY ").append(criteria.getSortColumn())
                .append(" ").append(criteria.getSortDirection());
        return jdbcTemplate.queryForList(sql.toString());
    }
}
