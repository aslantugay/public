package com.helix.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnalyticsDao {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public List<Object[]> notesByAuthorName(String authorName) {
        String jpql = "SELECT n.category, COUNT(n) FROM PatientNote n, User u " +
                "WHERE n.authorId = u.id AND u.username = '" + authorName + "' GROUP BY n.category";
        return em.createQuery(jpql).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> revenueByStatus(String tenantClause) {
        String sql = "SELECT status, SUM(amount) FROM invoices WHERE " + tenantClause + " GROUP BY status";
        return em.createNativeQuery(sql).getResultList();
    }
}
