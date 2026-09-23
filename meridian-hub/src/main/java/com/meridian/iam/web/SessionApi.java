package com.meridian.iam.web;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/session")
public interface SessionApi {

    @GetMapping("/export")
    Map<String, String> export();

    @PostMapping("/import")
    ResponseEntity<Void> importContext(@RequestBody Map<String, String> body);
}
