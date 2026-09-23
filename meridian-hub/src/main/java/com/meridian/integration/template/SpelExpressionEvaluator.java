package com.meridian.integration.template;

import java.util.Map;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

/**
 * Evaluates the small expression language used by automation rules and
 * notification templates (e.g. {@code order.amount > 1000}).
 */
@Component
public class SpelExpressionEvaluator implements ExpressionEvaluator {

    private final ExpressionParser parser = new SpelExpressionParser();

    @Override
    public Object evaluate(String expression, Map<String, Object> variables) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        if (variables != null) {
            variables.forEach(context::setVariable);
        }
        Expression parsed = parser.parseExpression(expression);
        return parsed.getValue(context);
    }
}
