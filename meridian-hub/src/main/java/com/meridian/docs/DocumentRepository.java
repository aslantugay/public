package com.meridian.docs;

import com.meridian.docs.domain.Document;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByTenantId(Long tenantId);

    Optional<Document> findByShareToken(String shareToken);
}
