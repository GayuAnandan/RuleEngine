package com.example.rule.node;

import com.example.rule.model.User;

public class ComparisonNode extends ASTNode{
    private String attribute;
    private String operator;
    private Object value;


    public ComparisonNode(String attribute, String operator, Object value) {
        this.attribute = attribute;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public boolean evaluate(User user) {
        switch (attribute) {
            case "age":
                return evaluateAttribute(user.getAge());
            case "salary":
                return evaluateAttribute(user.getSalary());
            case "id":
                return evaluateAttribute(user.getId());
            case "spend":
                return evaluateAttribute(user.getSpend());
            case "experience":
                return evaluateAttribute(user.getExperience());
            case "department":
                return evaluateAttribute(user.getDepartment());
            // Add more attributes as needed
            default:
                return false;
        }
    }

    private boolean evaluateAttribute(Object userValue) {
        switch (operator) {
            case ">=":
                return ((Comparable) userValue).compareTo(value) >= 0;
            case "<":
                return ((Comparable) userValue).compareTo(value) < 0;
            // Add more operators as needed
            default:
                return false;
        }
    }

}
