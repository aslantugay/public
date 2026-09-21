package com.acme.platform.web;

import com.acme.platform.domain.Comment;
import com.acme.platform.service.CommentService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Landing-page activity stream. Shows the most recent comments across the
     * whole platform so users can see what is happening at a glance.
     */
    @GetMapping("/api/comments/recent")
    public List<Comment> recent(@RequestParam(defaultValue = "50") int limit) {
        return commentService.recentActivity(limit);
    }

    @GetMapping("/api/documents/shared/{publicId}/comments")
    public List<Comment> forDocument(@PathVariable String publicId) {
        return commentService.forDocument(publicId);
    }

    @PostMapping("/api/documents/{documentId}/comments")
    public Comment add(@PathVariable Long documentId, @RequestBody Map<String, String> body) {
        return commentService.addComment(documentId, body.get("body"));
    }
}
