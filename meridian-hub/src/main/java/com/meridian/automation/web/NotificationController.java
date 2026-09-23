package com.meridian.automation.web;

import com.meridian.automation.domain.NotificationTemplate;
import com.meridian.automation.service.NotificationService;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController implements NotificationApi {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public NotificationTemplate createTemplate(Map<String, String> body) {
        return notificationService.create(body.get("name"), body.get("body"));
    }

    @Override
    public NotificationService.NotificationTemplateResult render(Long templateId,
                                                                Map<String, Object> variables) {
        return notificationService.render(templateId, variables);
    }
}
