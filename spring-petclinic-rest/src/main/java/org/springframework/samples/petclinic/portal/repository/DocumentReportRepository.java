package org.springframework.samples.petclinic.portal.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Native-query backed reporting for the document advanced-search feature.
 */
@Repository
public class DocumentReportRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Runs a saved advanced-search filter for an account.
     *
     * @param accountId the owning account
     * @param filter    the stored filter fragment built in the advanced-search UI
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> runFilter(Integer accountId, String filter) {
        String sql = "select id, title, tag from pet_document"
                + " where owner_account_id = " + accountId
                + " and (" + filter + ")";
        return entityManager.createNativeQuery(sql).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> search(Integer accountId, String keyword, String orderBy) {
        String sql = "select id, title, tag from pet_document"
                + " where owner_account_id = " + accountId
                + " and title like '%" + keyword + "%'"
                + " order by " + orderBy;
        return entityManager.createNativeQuery(sql).getResultList();
    }
}
