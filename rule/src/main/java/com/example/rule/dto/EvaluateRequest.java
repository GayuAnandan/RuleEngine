package com.example.rule.dto;

import lombok.Getter;
import lombok.Setter;
import com.example.rule.model.Node;


import java.util.Map;

@Getter
@Setter
public class EvaluateRequest {
    private Node ast; // The AST representing the rule
    private Map<String, Object> data;
}
