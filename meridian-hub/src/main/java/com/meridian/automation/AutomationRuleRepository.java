package com.meridian.automation;

import com.meridian.automation.domain.AutomationRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomationRuleRepository extends JpaRepository<AutomationRule, Long> {
}
