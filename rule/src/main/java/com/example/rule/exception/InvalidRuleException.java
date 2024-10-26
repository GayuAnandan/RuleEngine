package com.example.rule.exception;

public class InvalidRuleException extends RuntimeException{
    public InvalidRuleException(String message) {
        super(message);
    }
}
