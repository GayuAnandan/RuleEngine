package com.example.rule.validator;

import com.example.rule.exception.InvalidRuleException;

import java.util.List;
import java.util.Set;
import com.example.rule.model.Condition;

public class RuleValidator {
    private static final Set<String> VALID_ATTRIBUTES = Set.of("age", "department", "salary", "experience");

    public static void validateAttributes(List<Condition> conditions) {
        for (Condition condition : conditions) {
            if (!VALID_ATTRIBUTES.contains(condition.getField())) {
                throw new InvalidRuleException("Invalid attribute: " + condition.getField());
            }
        }
    }
}
