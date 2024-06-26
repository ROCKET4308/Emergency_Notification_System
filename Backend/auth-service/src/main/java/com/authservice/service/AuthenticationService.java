package com.authservice.service;

import com.authservice.entity.Role;
import com.authservice.entity.User;
import com.authservice.repository.UserRepository;
import com.authservice.request.AuthenticationRequest;
import com.authservice.request.RegisterRequest;
import com.authservice.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        if(repository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalArgumentException("User with this email is already exist");
        }else {
            repository.save(user);
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public Boolean verify(String authorizationHeader) {
        try {
            String jwtToken = authorizationHeader.replace("Bearer ", "");
            String email = jwtService.extractUsername(jwtToken);
            User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User with this email "+ email + " is not exist"));
            return jwtService.isTokenValid(jwtToken, user);
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }
}
