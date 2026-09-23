package com.meridian.automation.service;

import com.meridian.automation.NotificationTemplateRepository;
import com.meridian.automation.domain.NotificationTemplate;
import com.meridian.integration.template.ExpressionEvaluator;
import com.meridian.platform.security.CurrentUserService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationTemplateRepository templateRepository;
    private final ExpressionEvaluator expressionEvaluator;
    private final CurrentUserService currentUserService;

    public NotificationServiceImpl(NotificationTemplateRepository templateRepository,
                                   ExpressionEvaluator expressionEvaluator,
                                   CurrentUserService currentUserService) {
        this.templateRepository = templateRepository;
        this.expressionEvaluator = expressionEvaluator;
        this.currentUserService = currentUserService;
    }

    @Override
    public NotificationTemplate create(String name, String body) {
        NotificationTemplate template = new NotificationTemplate();
        template.setTenantId(currentUserService.tenantId());
        template.setName(name);
        template.setBody(body);
        return templateRepository.save(template);
    }

    @Override
    public NotificationTemplateResult render(Long templateId, Map<String, Object> variables) {
        NotificationTemplate template = templateRepository.findById(templateId).orElseThrow();
        Object value = expressionEvaluator.evaluate(template.getBody(), variables);
        return new NotificationTemplateResult(templateId, String.valueOf(value));
    }
}
