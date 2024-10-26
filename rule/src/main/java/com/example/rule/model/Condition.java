package com.example.rule.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Condition {
    private String field;
    private String operator;
    private Object value;
}
