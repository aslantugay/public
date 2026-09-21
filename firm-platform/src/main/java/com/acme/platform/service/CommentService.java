package com.acme.platform.service;

import com.acme.platform.domain.Comment;
import java.util.List;

public interface CommentService {

    List<Comment> recentActivity(int limit);

    List<Comment> forDocument(String documentPublicId);

    Comment addComment(Long documentId, String body);
}
