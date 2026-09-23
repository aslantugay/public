package com.meridian.automation.service;

import com.meridian.automation.domain.AutomationRule;
import java.util.Map;

public interface AutomationRuleService {

    AutomationRule create(String name, String conditionExpression, String actionJson);

    Object evaluate(Long ruleId, Map<String, Object> variables);

    AutomationRule importRule(String json);
}
