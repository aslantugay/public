package com.helix.repo;

import com.helix.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByOwnerUserId(Long ownerUserId);
    Optional<Document> findByShareToken(String shareToken);
    List<Document> findByVisibility(String visibility);
}
