package com.meridian.docs;

import com.meridian.docs.domain.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Comment> findByDocumentShareTokenOrderByCreatedAtDesc(String documentShareToken);
}
