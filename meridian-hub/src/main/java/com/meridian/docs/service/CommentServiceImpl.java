package com.meridian.docs.service;

import com.meridian.docs.CommentRepository;
import com.meridian.docs.DocumentRepository;
import com.meridian.docs.domain.Comment;
import com.meridian.docs.domain.Document;
import com.meridian.iam.UserRepository;
import com.meridian.iam.domain.User;
import com.meridian.platform.security.CurrentUserService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    public CommentServiceImpl(CommentRepository commentRepository,
                              DocumentRepository documentRepository,
                              UserRepository userRepository,
                              CurrentUserService currentUserService) {
        this.commentRepository = commentRepository;
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    public List<Comment> recentActivity(int limit) {
        return commentRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit));
    }

    @Override
    public List<Comment> forDocument(String shareToken) {
        return commentRepository.findByDocumentShareTokenOrderByCreatedAtDesc(shareToken);
    }

    @Override
    public Comment addComment(Long documentId, String body) {
        Document document = documentRepository.findById(documentId).orElseThrow();
        User author = currentUserService.userId() == null ? null
                : userRepository.findById(currentUserService.userId()).orElse(null);

        Comment comment = new Comment();
        comment.setDocumentShareToken(document.getShareToken());
        comment.setDocumentTitle(document.getTitle());
        comment.setAuthorEmail(author == null ? "unknown" : author.getEmail());
        comment.setBody(body);
        return commentRepository.save(comment);
    }
}
