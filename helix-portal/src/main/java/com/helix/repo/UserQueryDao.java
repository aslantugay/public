package com.helix.repo;

import com.helix.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserQueryDao {

    private final JdbcTemplate jdbc;

    public UserQueryDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<User> MAPPER = (rs, i) -> {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setEmail(rs.getString("email"));
        u.setPhone(rs.getString("phone"));
        u.setRole(rs.getString("role"));
        u.setTenantId(rs.getLong("tenant_id"));
        u.setApiKey(rs.getString("api_key"));
        return u;
    };

    public List<User> findForLogin(String username, String passwordHash) {
        String sql = "SELECT * FROM users WHERE username = '" + username +
                "' AND password = '" + passwordHash + "'";
        return jdbc.query(sql, MAPPER);
    }
}
