package com.meridian.automation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.meridian.automation.AutomationRuleRepository;
import com.meridian.automation.domain.AutomationRule;
import com.meridian.integration.template.ExpressionEvaluator;
import com.meridian.platform.security.AbstractTenantService;
import com.meridian.platform.security.CurrentUserService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AutomationRuleServiceImpl extends AbstractTenantService implements AutomationRuleService {

    private final AutomationRuleRepository ruleRepository;
    private final ExpressionEvaluator expressionEvaluator;
    private final ObjectMapper importMapper;

    public AutomationRuleServiceImpl(CurrentUserService currentUserService,
                                     AutomationRuleRepository ruleRepository,
                                     ExpressionEvaluator expressionEvaluator) {
        super(currentUserService);
        this.ruleRepository = ruleRepository;
        this.expressionEvaluator = expressionEvaluator;

        this.importMapper = new ObjectMapper();
        this.importMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL);
    }

    @Override
    public AutomationRule create(String name, String conditionExpression, String actionJson) {
        AutomationRule rule = new AutomationRule();
        rule.setTenantId(currentTenantId());
        rule.setName(name);
        rule.setConditionExpression(conditionExpression);
        rule.setActionJson(actionJson);
        return ruleRepository.save(rule);
    }

    @Override
    public Object evaluate(Long ruleId, Map<String, Object> variables) {
        AutomationRule rule = ruleRepository.findById(ruleId).orElseThrow();
        return expressionEvaluator.evaluate(rule.getConditionExpression(), variables);
    }

    @Override
    public AutomationRule importRule(String json) {
        try {
            AutomationRule rule = importMapper.readValue(json, AutomationRule.class);
            rule.setTenantId(currentTenantId());
            return ruleRepository.save(rule);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid rule document", e);
        }
    }
}
