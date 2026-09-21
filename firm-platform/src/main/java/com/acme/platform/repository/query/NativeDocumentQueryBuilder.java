package com.acme.platform.repository.query;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class NativeDocumentQueryBuilder implements DocumentQueryBuilder {

    private static final String BASE =
            "SELECT id, firm_id, unit_id, owner_user_id, title, classification, created_at "
                    + "FROM documents";

    @Override
    public String buildSearchSql(DocumentSearchCriteria criteria) {
        StringBuilder sql = new StringBuilder(BASE);
        sql.append(" WHERE firm_id = ").append(criteria.getFirmId());

        if (criteria.getKeyword() != null && !criteria.getKeyword().isBlank()) {
            sql.append(" AND (title LIKE '%").append(criteria.getKeyword()).append("%'");
            sql.append(" OR notes LIKE '%").append(criteria.getKeyword()).append("%')");
        }
        if (criteria.getClassification() != null && !criteria.getClassification().isBlank()) {
            sql.append(" AND classification = '").append(criteria.getClassification()).append("'");
        }

        sql.append(" ORDER BY ").append(criteria.getSortColumn())
                .append(" ").append(criteria.getSortDirection());
        return sql.toString();
    }

    @Override
    public Map<String, Object> describe(DocumentSearchCriteria criteria) {
        Map<String, Object> meta = new LinkedHashMap<>();
        meta.put("firmId", criteria.getFirmId());
        meta.put("keyword", criteria.getKeyword());
        meta.put("sql", buildSearchSql(criteria));
        return meta;
    }
}
