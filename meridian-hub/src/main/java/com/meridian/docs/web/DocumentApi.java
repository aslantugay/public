package com.meridian.docs.web;

import com.meridian.docs.domain.Document;
import com.meridian.docs.dto.DocumentCreateRequest;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/documents")
public interface DocumentApi {

    @GetMapping("/search")
    List<Map<String, Object>> search(@RequestParam(required = false) String q,
                                     @RequestParam(required = false) String classification,
                                     @RequestParam(defaultValue = "created_at") String sort,
                                     @RequestParam(defaultValue = "desc") String dir);

    @GetMapping("/{documentId}")
    ResponseEntity<Document> get(@PathVariable Long documentId);

    @GetMapping("/shared/{shareToken}")
    ResponseEntity<Document> shared(@PathVariable String shareToken);

    @GetMapping("/{documentId}/raw")
    ResponseEntity<byte[]> raw(@PathVariable Long documentId, @RequestParam String file);

    @PostMapping
    ResponseEntity<Document> create(@RequestBody DocumentCreateRequest request);
}
