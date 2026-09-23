package com.meridian.automation.web;

import com.meridian.automation.domain.AutomationRule;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/automation/rules")
public interface AutomationRuleApi {

    @PostMapping
    AutomationRule create(@RequestBody Map<String, String> body);

    @PostMapping("/{ruleId}/evaluate")
    Object evaluate(@PathVariable Long ruleId, @RequestBody Map<String, Object> variables);

    @PostMapping("/import")
    AutomationRule importRule(@RequestBody String json);
}
