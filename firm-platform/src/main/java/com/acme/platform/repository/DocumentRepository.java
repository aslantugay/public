package com.acme.platform.repository;

import com.acme.platform.domain.Document;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByFirmId(Long firmId);

    Optional<Document> findByPublicId(String publicId);
}
