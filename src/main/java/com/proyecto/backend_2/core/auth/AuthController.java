package com.proyecto.backend_2.core.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.backend_2.core.auth.dtos.AuthResponse;
import com.proyecto.backend_2.core.auth.dtos.ChangePasswordRequest;
import com.proyecto.backend_2.core.auth.dtos.LoginRequest;
import com.proyecto.backend_2.core.auth.dtos.RegisterRequest;
import com.proyecto.backend_2.core.auth.services.AuthService;
import com.proyecto.backend_2.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @GetMapping("/me")
    public String me() {
        return "hello world";
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest login) {
        return ResponseEntity.ok(service.login(login));
    }

    @PostMapping("/login2")
    public ResponseEntity<ApiResponse> login2(@RequestBody LoginRequest login) {
        return service.login2(login);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest register) {
        return service.register(register);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        return service.changePassword(request);
    }
}
