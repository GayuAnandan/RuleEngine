package com.example.rule.controller;

import com.example.rule.model.User;
import com.example.rule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        // Find the existing user by ID
        Optional<User> existingUserOptional = userService.findById(id);

        if (!existingUserOptional.isPresent()) {
            return ResponseEntity.notFound().build(); // Return 404 if user not found
        }

        User existingUser = existingUserOptional.get();

        // Update the fields
        existingUser.setSalary(user.getSalary());
        existingUser.setAge(user.getAge());
        existingUser.setDepartment(user.getDepartment());
        existingUser.setSpend(user.getSpend());
        existingUser.setExperience(user.getExperience());

        // Save the updated user
        User updatedUser = userService.save(existingUser);

        return ResponseEntity.ok(updatedUser); // Return updated user with 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build(); // Return 404 if user not found
        }

        userService.deleteUserById(id); // Implement this method in UserService
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }

}
