package com.helix.repo;

import com.helix.model.Patient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientQueryDao {

    private final JdbcTemplate jdbc;

    public PatientQueryDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Patient> MAPPER = (rs, i) -> {
        Patient p = new Patient();
        p.setId(rs.getLong("id"));
        p.setOwnerUserId(rs.getLong("owner_user_id"));
        p.setTenantId(rs.getLong("tenant_id"));
        p.setName(rs.getString("name"));
        p.setSsn(rs.getString("ssn"));
        p.setDiagnosis(rs.getString("diagnosis"));
        p.setBloodType(rs.getString("blood_type"));
        return p;
    };

    public List<Patient> searchByName(String name) {
        String sql = "SELECT * FROM patients WHERE name LIKE '%" + name + "%'";
        return jdbc.query(sql, MAPPER);
    }

    public List<Patient> listOrdered(Long tenantId, String sort) {
        String sql = "SELECT * FROM patients WHERE tenant_id = " + tenantId + " ORDER BY " + sort;
        return jdbc.query(sql, MAPPER);
    }

    public List<Patient> execFilter(String whereClause) {
        String sql = "SELECT * FROM patients WHERE " + whereClause;
        return jdbc.query(sql, MAPPER);
    }

    public List<java.util.Map<String, Object>> aggregateByColumn(String groupColumn) {
        String sql = "SELECT " + groupColumn + " AS k, COUNT(*) AS c FROM patients GROUP BY " + groupColumn;
        return jdbc.queryForList(sql);
    }
}
