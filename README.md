# RuleEngine with AST

# Objective
simple 3-tier rule engine application(Simple UI, API and Backend, Data) to determine user eligibility based on attributes like age, department, income, spend etc.

# Artifacts
CodeBase: [GitHub Respository](https://github.com/GayuAnandan/RuleEngine/)

# Data Structure
The core data structure for representing the AST

# API Design
Functions
createRule(String ruleString):

Takes a string representing a rule and returns a Node object representing the corresponding AST.
combineRules(List<String> rules):

Accepts a list of rule strings, combines them into a single AST, and returns the root node of the combined AST, optimizing for efficiency and minimizing redundant checks.
evaluateRule(Node astNode, Map<String, Object> data):

Takes the combined rule's AST and a map of user attributes, evaluates the rule against the provided data, and returns True if the user meets the criteria, False otherwise.

# Dependencies
## Required Software
* MySQL for the database
* Java 17 for the application code
* Maven for dependency management and build

# Test Cases
* Create individual rules from the examples using createRule and verify their AST representation.
* Combine the example rules using combineRules and ensure the resulting AST reflects the combined logic.
* Implement sample JSON data and test evaluateRule for different scenarios:
* Explore combining additional rules and test the functionality.

# Design Choices
* AST Representation: The AST structure allows for efficient evaluation and dynamic rule modification.
* Database Choice: MySQL was selected for its robust querying capabilities and transactional support.

# Conclusion
This rule engine application provides a robust framework for defining and evaluating complex eligibility criteria using ASTs. The dynamic capabilities of the engine allow for flexible rule management and evaluation.
