package com.authservice.controller;

import com.authservice.request.AuthenticationRequest;
import com.authservice.request.RegisterRequest;
import com.authservice.response.AuthenticationResponse;
import com.authservice.service.AuthenticationService;
import com.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

    @GetMapping("/verify")
    public Boolean verify(
            @RequestHeader("Authorization") String token
    ){
        return authenticationService.verify(token);
    }

    @GetMapping("/extractEmail")
    public String extractEmail(
            @RequestHeader("Authorization") String authorizationHeader
    ){
        String token = authorizationHeader.replace("Bearer ", "");
        return jwtService.extractUsername(token);
    }
}
