package com.example.rule.node;

import com.example.rule.model.User;

public abstract class ASTNode {
    public abstract boolean evaluate(User user);
}
