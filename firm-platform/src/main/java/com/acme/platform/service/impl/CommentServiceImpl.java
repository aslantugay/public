package com.acme.platform.service.impl;

import com.acme.platform.domain.Comment;
import com.acme.platform.domain.Document;
import com.acme.platform.domain.User;
import com.acme.platform.repository.CommentRepository;
import com.acme.platform.repository.DocumentRepository;
import com.acme.platform.security.CurrentUserService;
import com.acme.platform.service.CommentService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final DocumentRepository documentRepository;
    private final CurrentUserService currentUserService;

    public CommentServiceImpl(CommentRepository commentRepository,
                              DocumentRepository documentRepository,
                              CurrentUserService currentUserService) {
        this.commentRepository = commentRepository;
        this.documentRepository = documentRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public List<Comment> recentActivity(int limit) {
        return commentRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit));
    }

    @Override
    public List<Comment> forDocument(String documentPublicId) {
        return commentRepository.findByDocumentPublicIdOrderByCreatedAtDesc(documentPublicId);
    }

    @Override
    public Comment addComment(Long documentId, String body) {
        Document document = documentRepository.findById(documentId).orElseThrow();
        User author = currentUserService.currentUser();

        Comment comment = new Comment();
        comment.setDocumentPublicId(document.getPublicId());
        comment.setDocumentTitle(document.getTitle());
        comment.setAuthorEmail(author == null ? "unknown" : author.getEmail());
        comment.setBody(body);
        return commentRepository.save(comment);
    }
}
