package com.meridian.integration.template;

import java.util.Map;

public interface ExpressionEvaluator {

    Object evaluate(String expression, Map<String, Object> variables);
}
