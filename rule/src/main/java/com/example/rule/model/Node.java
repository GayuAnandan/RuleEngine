package com.example.rule.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
    private String type; // Type of the node: "operator" or "operand"
    private Node left;   // Reference to the left child Node
    private Node right;  // Reference to the right child Node
    private String value;

    // Constructor for operator nodes
    public Node(String type, Node left, Node right, String value) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value;
    }

    // Constructor for operand nodes
    public Node(String type, String value) {
        this.type = type;
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
