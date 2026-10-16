package com.macro.mall.portal.reward.service.impl;

import com.macro.mall.portal.reward.service.RewardPromotionService;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

@Service
public class RewardPromotionServiceImpl implements RewardPromotionService {

    @Override
    public Object evalRule(String expr) {
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(expr).getValue();
    }
}
