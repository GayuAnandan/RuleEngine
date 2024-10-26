package com.example.rule.service;

import com.example.rule.model.Node;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RuleServiceTest {
    @Autowired
    private RuleService ruleService;

    @Test
    public void testCreateIndividualRules() {
        Node rule1 = ruleService.createRule("age > 30");
        System.out.println("AST for rule1: " + ruleService.serializeAST(rule1));
        assertEquals("(age > 30)", ruleService.serializeAST(rule1)); // Expected AST representation

        Node rule2 = ruleService.createRule("salary < 50000");
        System.out.println("AST for rule2: " + ruleService.serializeAST(rule2));
        assertEquals("(salary < 50000)", ruleService.serializeAST(rule2)); // Expected AST representation

        Node rule3 = ruleService.createRule("status = 'active'");
        System.out.println("AST for rule3: " + ruleService.serializeAST(rule3));
        assertEquals("(status = 'active')", ruleService.serializeAST(rule3)); // Expected AST representation
    }

    @Test
    public void testCombineRules() {
        List<String> rules = List.of("age > 30", "salary < 50000");
        Node combinedAST = ruleService.combineRules(rules);
        System.out.println("Combined AST: " + ruleService.serializeAST(combinedAST));
        assertEquals("((age > 30) AND (salary < 50000))", ruleService.serializeAST(combinedAST)); // Expected combined AST representation
    }

    @Test
    public void testEvaluateRule() {
        Map<String, Object> sampleData = Map.of(
                "age", 35,
                "salary", 40000,
                "status", "active"
        );

        Node ruleAST = ruleService.parseRule("age > 30 AND salary < 50000");
        boolean result = ruleService.evaluateRule(ruleAST, sampleData);
        System.out.println("Evaluation result for rule: " + result);
        assertTrue(result); // Expected: true
    }

    @Test
    public void testEvaluateRuleWithDifferentScenarios() {
        // Scenario 1: Should evaluate to false
        Map<String, Object> sampleData1 = Map.of(
                "age", 25,
                "salary", 60000,
                "status", "inactive"
        );

        Node ruleAST1 = ruleService.parseRule("age > 30 AND status = 'active'");
        boolean result1 = ruleService.evaluateRule(ruleAST1, sampleData1);
        System.out.println("Evaluation result for sampleData1: " + result1);
        assertFalse(result1); // Expected: false

        // Scenario 2: Should evaluate to true
        Map<String, Object> sampleData2 = Map.of(
                "age", 32,
                "salary", 45000,
                "status", "active"
        );

        Node ruleAST2 = ruleService.parseRule("age > 30 AND salary < 50000");
        boolean result2 = ruleService.evaluateRule(ruleAST2, sampleData2);
        System.out.println("Evaluation result for sampleData2: " + result2);
        assertTrue(result2); // Expected: true
    }

    @Test
    public void testCombineAndEvaluateAdditionalRules() {
        List<String> rules = List.of("age > 30", "salary < 50000", "status = 'inactive'");
        Node combinedAST = ruleService.combineRules(rules);

        Map<String, Object> sampleData = Map.of(
                "age", 32,
                "salary", 45000,
                "status", "active"
        );

        boolean result = ruleService.evaluateRule(combinedAST, sampleData);
        System.out.println("Evaluation result for combined rules: " + result);
        assertFalse(result); // Expected: false (status inactive)
    }
}
