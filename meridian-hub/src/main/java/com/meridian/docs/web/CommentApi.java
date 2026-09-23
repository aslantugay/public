package com.meridian.docs.web;

import com.meridian.docs.domain.Comment;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CommentApi {

    @GetMapping("/api/comments/recent")
    List<Comment> recent(@RequestParam(defaultValue = "50") int limit);

    @GetMapping("/api/documents/shared/{shareToken}/comments")
    List<Comment> forDocument(@PathVariable String shareToken);

    @PostMapping("/api/documents/{documentId}/comments")
    Comment add(@PathVariable Long documentId, @RequestBody Map<String, String> body);
}
