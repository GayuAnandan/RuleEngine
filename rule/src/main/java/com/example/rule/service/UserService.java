package com.example.rule.service;

import com.example.rule.model.Rule;
import com.example.rule.model.User;
import com.example.rule.node.ASTNode;
import com.example.rule.node.ComparisonNode;
import com.example.rule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean evaluateUser(User user, Rule rule) {
        // Example of creating a ComparisonNode and evaluating
        ASTNode astNode = new ComparisonNode("age", ">=", 18);
        return astNode.evaluate(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id); // Use the repository to find by ID
    }

    public User save(User user) {
        return userRepository.save(user); // Save the user (used for updates)
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

}
