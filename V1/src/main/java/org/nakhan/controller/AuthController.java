package org.nakhan.controller;

import org.nakhan.dto.LoginRequest;
import org.nakhan.dto.LoginResponse;
import org.nakhan.entity.User;
import org.nakhan.repository.UserRepository;
import org.nakhan.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> user = userRepository.findByUsername(userDetails.getUsername());

            if (user.isPresent()) {
                String token = jwtUtil.generateToken(userDetails);

                LoginResponse loginResponse = new LoginResponse(
                    token,
                    user.get().getUsername(),
                    user.get().getRole().name(),
                    user.get().getId()
                );

                return ResponseEntity.ok(loginResponse);
            }

            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // Remove "Bearer " prefix if present
            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

            String username = jwtUtil.extractUsername(jwtToken);
            Optional<User> user = userRepository.findByUsername(username);

            if (user.isPresent()) {
                UserDetails userDetails = user.get();
                boolean isValid = jwtUtil.validateToken(jwtToken, userDetails);
                return ResponseEntity.ok(isValid);
            }

            return ResponseEntity.ok(false);

        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}
