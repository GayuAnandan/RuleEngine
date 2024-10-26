package com.example.rule.controller;

import com.example.rule.dto.EvaluateRequest;
import com.example.rule.model.Rule;
import com.example.rule.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.rule.model.Node;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
public class RuleController {
    @Autowired
    private RuleService ruleService;

    @PostMapping("/create_rule")
    public Node createRule(@RequestBody String ruleString) {
        return ruleService.createRule(ruleString);
    }

    @PostMapping("/combine_rules")
    public Node combineRules(@RequestBody List<String> rules) {
        return ruleService.combineRules(rules);
    }

    @PostMapping("/evaluate_rule")
    public boolean evaluateRule(@RequestBody EvaluateRequest request) {
        return ruleService.evaluateRule(request.getAst(), request.getData());
    }

    @GetMapping
    public List<Rule> getAllRules() {
        return ruleService.getAllRules();
    }

}
