package com.meridian.docs.service;

import com.meridian.docs.domain.Comment;
import java.util.List;

public interface CommentService {

    List<Comment> recentActivity(int limit);

    List<Comment> forDocument(String shareToken);

    Comment addComment(Long documentId, String body);
}
