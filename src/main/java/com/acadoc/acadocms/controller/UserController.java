package com.acadoc.acadocms.controller;

import com.acadoc.acadocms.model.User;
import com.acadoc.acadocms.repository.UserRepository;
import com.acadoc.acadocms.util.JWTUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public UserController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/users")
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userRepository.save(user);
        return "User saved successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        var dbUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(user.getPasswordHash(), dbUser.getPasswordHash())) {
            return jwtUtil.generateToken(dbUser.getEmail());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @GetMapping("/secure")
    public String secure() {
        return "You are authenticated!";
    }

    @DeleteMapping("/deleteuser")
    public void delete(@RequestBody User user) {
        userRepository.delete(user);
    }
}
