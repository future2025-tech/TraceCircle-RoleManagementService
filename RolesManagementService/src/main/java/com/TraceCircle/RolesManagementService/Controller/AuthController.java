package com.TraceCircle.RolesManagementService.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TraceCircle.RolesManagementService.DTO.AuthResponseDTO;
import com.TraceCircle.RolesManagementService.DTO.LoginRequestDTO;
import com.TraceCircle.RolesManagementService.DTO.SignUpDTO;
import com.TraceCircle.RolesManagementService.DTO.SignupRequest;
import com.TraceCircle.RolesManagementService.DTO.SystemAdminOnboardingDTO;
import com.TraceCircle.RolesManagementService.Entity.SystemAdminOnboardingEntity;
import com.TraceCircle.RolesManagementService.Exception.ApiException;
import com.TraceCircle.RolesManagementService.Repository.SystemAdminOnboardingRepository;
import com.TraceCircle.RolesManagementService.Service.AuthManagementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthManagementService authService;
    private final SystemAdminOnboardingRepository onboardingRepo;

    @PostMapping("/onboarding")
    public ResponseEntity<SystemAdminOnboardingDTO> onboard(@RequestBody SystemAdminOnboardingDTO dto) {
       
    	log.info("Onboarding request: {}", dto.getSystemName());
        
    	return ResponseEntity.ok(authService.onboardSystemAdmin(dto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpDTO> signup(@Valid @RequestBody SignupRequest req) {

        log.info("Signup request received");

        SystemAdminOnboardingEntity latestAdmin =
                onboardingRepo.findTopByOrderByIdDesc().orElseThrow(
                        () -> new ApiException("No system admin onboarded yet"));

        Long systemAdminId = latestAdmin.getId();

        log.info("Automatically assigning systemAdminId={} and email={}",
                systemAdminId, latestAdmin.getSystemEmailId());

        // 2️⃣ Inject system email automatically into DTO
        req.setEmailId(latestAdmin.getSystemEmailId());

        // 3️⃣ Call service with systemAdminId
        SignUpDTO response = authService.signup(req);

        return ResponseEntity.ok(response);
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
