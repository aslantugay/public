package com.acme.platform.web;

import com.acme.platform.service.SessionService;
import com.acme.platform.web.dto.SessionImportRequest;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/export")
    public Map<String, String> export() {
        return Map.of("context", sessionService.exportContext());
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importContext(@RequestBody SessionImportRequest request) {
        sessionService.importContext(request.getToken());
        return ResponseEntity.noContent().build();
    }
}
