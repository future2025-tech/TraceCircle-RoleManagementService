package com.TraceCircle.RolesManagementService.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TraceCircle.RolesManagementService.DTO.*;
import com.TraceCircle.RolesManagementService.Service.AuthManagementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthManagementService authService;

    @PostMapping("/onboarding")
    public ResponseEntity<SystemAdminOnboardingDTO> onboard(@RequestBody SystemAdminOnboardingDTO dto) {
       
    	log.info("Onboarding request: {}", dto.getSystemName());
        
    	return ResponseEntity.ok(authService.onboardSystemAdmin(dto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpDTO> signup(@RequestBody SignupRequest req) {
       
    	log.info("Signup request: {}", req.getEmailId());
        
    	return ResponseEntity.ok(authService.signup(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO req) {
       
    	log.info("Login request: {}", req.getEmailId());
        
    	return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody String refreshToken) {
        
    	return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
