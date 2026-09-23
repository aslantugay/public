package com.meridian.docs.web;

import com.meridian.docs.domain.Comment;
import com.meridian.docs.service.CommentService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController implements CommentApi {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public List<Comment> recent(int limit) {
        return commentService.recentActivity(limit);
    }

    @Override
    public List<Comment> forDocument(String shareToken) {
        return commentService.forDocument(shareToken);
    }

    @Override
    public Comment add(Long documentId, Map<String, String> body) {
        return commentService.addComment(documentId, body.get("body"));
    }
}
