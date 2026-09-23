package com.meridian.docs.web;

import com.meridian.docs.domain.SavedFilter;
import com.meridian.docs.dto.SavedFilterRequest;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/documents/filters")
public interface SavedFilterApi {

    @PostMapping
    SavedFilter create(@RequestBody SavedFilterRequest request);

    @GetMapping("/{filterId}/run")
    List<Map<String, Object>> run(@PathVariable Long filterId);
}
