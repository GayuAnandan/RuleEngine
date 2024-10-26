package com.example.rule;

import com.example.rule.model.Node;
import com.example.rule.service.RuleService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.rule.repository.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;


import java.util.*;

@SpringBootTest
class RuleengineApplicationTests {

	@Test
	void contextLoads() {
	}

	@InjectMocks
	private RuleService ruleService;

	@Mock
	private RuleRepository ruleRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateRule() {
		String ruleString = "age > 30";
		Node ast = ruleService.createRule(ruleString);
		assertNotNull(ast);
		// Add more assertions to check the structure of the AST
	}

	@Test
	public void testCombineRules() {
		List<String> rules = Arrays.asList(
				"age > 30 AND department = 'Sales'",
				"age < 25 AND department = 'Marketing'"
		);
		Node combinedAST = ruleService.combineRules(rules);
		assertNotNull(combinedAST);
		// Assert expected structure of combinedAST
	}

	@Test
	public void testEvaluateRule() {
		Node ast = ruleService.createRule("age > 30 AND salary > 50000");
		Map<String, Object> userData = new HashMap<>();
		userData.put("age", 35);
		userData.put("salary", 60000);

		boolean result = ruleService.evaluateRule(ast, userData);
		assertTrue(result); // Expected: true
	}


}
