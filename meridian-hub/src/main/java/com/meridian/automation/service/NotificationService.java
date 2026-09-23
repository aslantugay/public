package com.meridian.automation.service;

import com.meridian.automation.domain.NotificationTemplate;
import java.util.Map;

public interface NotificationService {

    NotificationTemplate create(String name, String body);

    NotificationTemplateResult render(Long templateId, Map<String, Object> variables);

    class NotificationTemplateResult {
        private final Long templateId;
        private final String rendered;

        public NotificationTemplateResult(Long templateId, String rendered) {
            this.templateId = templateId;
            this.rendered = rendered;
        }

        public Long getTemplateId() {
            return templateId;
        }

        public String getRendered() {
            return rendered;
        }
    }
}
