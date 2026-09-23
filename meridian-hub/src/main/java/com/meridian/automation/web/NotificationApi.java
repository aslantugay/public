package com.meridian.automation.web;

import com.meridian.automation.domain.NotificationTemplate;
import com.meridian.automation.service.NotificationService;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/notifications")
public interface NotificationApi {

    @PostMapping("/templates")
    NotificationTemplate createTemplate(@RequestBody Map<String, String> body);

    @PostMapping("/templates/{templateId}/render")
    NotificationService.NotificationTemplateResult render(@PathVariable Long templateId,
                                                          @RequestBody Map<String, Object> variables);
}
