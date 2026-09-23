package com.meridian.iam.web;

import com.meridian.iam.service.SessionService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController implements SessionApi {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public Map<String, String> export() {
        return Map.of("context", sessionService.exportContext());
    }

    @Override
    public ResponseEntity<Void> importContext(Map<String, String> body) {
        sessionService.importContext(body.get("token"));
        return ResponseEntity.noContent().build();
    }
}
