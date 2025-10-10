package com.acadoc.acadocms.service;

import com.acadoc.acadocms.model.User;
import com.acadoc.acadocms.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public String loginAndGenerateToken(String email, String password) {
        User user = userService.getUserByEmail(email);
        if(!passwordEncoder.matches(password, user.getPasswordHash())){
            throw new RuntimeException("Incorrect password");
        }return jwtUtil.generateToken(user.getEmail());
    }

    public boolean validateToken(String token) {
        return  jwtUtil.validateToken(token);
    }

    public String getEmailFromToken(String token) {
        return jwtUtil.extractEmail(token);
    }

    public User getUserFromToken(String token) {
        String email = getEmailFromToken(token);
        return userService.getUserByEmail(email);
    }
}
