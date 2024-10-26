package com.example.rule.service;

import com.example.rule.model.Condition;
import com.example.rule.model.Rule;
import com.example.rule.repository.RuleRepository;
import com.example.rule.validator.RuleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.rule.model.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Service
public class RuleService {
    @Autowired
    private RuleRepository ruleRepository;

    public Node createRule(String ruleString) {
        List<Condition> conditions = parseConditions(ruleString);
        RuleValidator.validateAttributes(conditions);
        Node ast = parseRule(ruleString);

        Rule rule = new Rule();
        rule.setRuleString(ruleString);
        rule.setAst(serializeAST(ast));
        rule.setCreatedAt(System.currentTimeMillis());

        return ruleRepository.save(rule) != null ? ast : null;
    }

    public void updateRule(Long id, String ruleString) {
        validateRuleString(ruleString);
        Rule existingRule = ruleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rule not found with id: " + id));

        List<Condition> conditions = parseConditions(ruleString);
        RuleValidator.validateAttributes(conditions);

        Node ast = parseRule(ruleString);
        existingRule.setRuleString(ruleString);
        existingRule.setAst(serializeAST(ast));
        ruleRepository.save(existingRule);
    }

    private void validateRuleString(String ruleString) {
        if (!ruleString.matches(".*\\b(AND|OR)\\b.*")) {
            throw new IllegalArgumentException("Rule string must contain at least one logical operator (AND/OR).");
        }
        if (!areParenthesesBalanced(ruleString)) {
            throw new IllegalArgumentException("Parentheses are not balanced in the rule string.");
        }
    }

    private boolean areParenthesesBalanced(String ruleString) {
        Stack<Character> stack = new Stack<>();
        for (char c : ruleString.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) return false;
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    private List<Condition> parseConditions(String ruleString) {
        List<Condition> conditions = new ArrayList<>();
        String[] tokens = ruleString.split(" ");

        for (String token : tokens) {
            if (token.matches("^[a-zA-Z]+\\s*[<>]?=\\s*\\d+$")) {
                String[] parts = token.split(" ");
                Condition condition = new Condition();
                condition.setField(parts[0]);
                condition.setOperator(parts[1]);
                condition.setValue(parts[2]);
                conditions.add(condition);
            } else if (token.matches("^[a-zA-Z]+\\s*=\\s*'[^']*'$")) {
                String[] parts = token.split("=");
                Condition condition = new Condition();
                condition.setField(parts[0]);
                condition.setOperator("=");
                condition.setValue(parts[1].replace("'", ""));
                conditions.add(condition);
            }
        }

        return conditions;
    }

    public Node combineRules(List<String> rules) {
        Node combinedAST = null;
        for (String rule : rules) {
            Node ast = parseRule(rule);
            combinedAST = (combinedAST == null) ? ast : combineASTs(combinedAST, ast);
        }
        return combinedAST;
    }

    public boolean evaluateRule(Node ast, Map<String, Object> data) {
        return evaluateAST(ast, data);
    }

    public Node parseRule(String ruleString) {
        Stack<Node> stack = new Stack<>();
        String[] tokens = ruleString.split(" ");

        for (String token : tokens) {
            if (token.equals("AND") || token.equals("OR")) {
                Node right = stack.pop();
                Node left = stack.pop();
                stack.push(new Node("operator", left, right, token));
            } else {
                stack.push(new Node("operand", null, null, token));
            }
        }
        return stack.isEmpty() ? null : stack.pop();
    }

    private Node combineASTs(Node ast1, Node ast2) {
        return new Node("operator", ast1, ast2, "AND");
    }

    private boolean evaluateAST(Node ast, Map<String, Object> data) {
        if (ast.getType().equals("operand")) {
            return evaluateCondition(ast.getValue(), data);
        } else if (ast.getType().equals("operator")) {
            boolean leftResult = evaluateAST(ast.getLeft(), data);
            boolean rightResult = evaluateAST(ast.getRight(), data);
            return ast.getValue().equals("AND") ? leftResult && rightResult : leftResult || rightResult;
        }
        return false;
    }

    private boolean evaluateCondition(String condition, Map<String, Object> data) {
        String[] parts = condition.split(" ");
        String attribute = parts[0];
        String operator = parts[1];
        String value = parts[2];

        Object attributeValue = data.get(attribute);
        switch (operator) {
            case ">":
                return ((Number) attributeValue).doubleValue() > Double.parseDouble(value);
            case "<":
                return ((Number) attributeValue).doubleValue() < Double.parseDouble(value);
            case "=":
                return attributeValue.toString().equals(value.replace("'", ""));
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    public String serializeAST(Node ast) {
        if (ast.getType().equals("operand")) {
            return ast.getValue();
        } else {
            return "(" + serializeAST(ast.getLeft()) + " " + ast.getValue() + " " + serializeAST(ast.getRight()) + ")";
        }
    }

    public String getASTRepresentation(Node ast) {
        return serializeAST(ast);
    }

    public List<Rule> getAllRules() {
        return ruleRepository.findAll();
    }

    public Rule saveRule(Rule rule) {
        return ruleRepository.save(rule);
    }
}
