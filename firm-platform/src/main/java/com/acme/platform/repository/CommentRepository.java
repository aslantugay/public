package com.acme.platform.repository;

import com.acme.platform.domain.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Comment> findByDocumentPublicIdOrderByCreatedAtDesc(String documentPublicId);
}
