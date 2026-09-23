package com.meridian.automation.web;

import com.meridian.automation.domain.AutomationRule;
import com.meridian.automation.service.AutomationRuleService;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutomationRuleController implements AutomationRuleApi {

    private final AutomationRuleService automationRuleService;

    public AutomationRuleController(AutomationRuleService automationRuleService) {
        this.automationRuleService = automationRuleService;
    }

    @Override
    public AutomationRule create(Map<String, String> body) {
        return automationRuleService.create(
                body.get("name"), body.get("condition"), body.get("actionJson"));
    }

    @Override
    public Object evaluate(Long ruleId, Map<String, Object> variables) {
        return automationRuleService.evaluate(ruleId, variables);
    }

    @Override
    public AutomationRule importRule(String json) {
        return automationRuleService.importRule(json);
    }
}
