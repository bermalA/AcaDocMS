package com.acadoc.acadocms.controller;

import com.acadoc.acadocms.dto.request.LoginRequest;
import com.acadoc.acadocms.dto.request.RegisterRequest;
import com.acadoc.acadocms.dto.response.LoginResponse;
import com.acadoc.acadocms.service.AuthService;
import com.acadoc.acadocms.service.UserService;
import com.acadoc.acadocms.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        userService.registerUser(request.getFullName(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.loginAndGenerateToken(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

}
