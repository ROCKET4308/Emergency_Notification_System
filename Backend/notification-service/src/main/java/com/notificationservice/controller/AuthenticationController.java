package com.notificationservice.controller;

import com.notificationservice.request.AuthenticationRequest;
import com.notificationservice.response.AuthenticationResponse;
import com.notificationservice.service.AuthenticationService;
import com.notificationservice.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));

    }

    @GetMapping("/verify")
    public Boolean verify(
            @RequestHeader("Authorization") String token
    ){
        return service.verify(token);
    }
}
