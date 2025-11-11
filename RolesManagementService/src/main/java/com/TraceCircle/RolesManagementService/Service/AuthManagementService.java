package com.TraceCircle.RolesManagementService.Service;

import com.TraceCircle.RolesManagementService.DTO.*;

public interface AuthManagementService {

    SystemAdminOnboardingDTO onboardSystemAdmin(SystemAdminOnboardingDTO dto);

    SignUpDTO signup(SignupRequest request);

    AuthResponseDTO login(LoginRequestDTO request);
    
    AuthResponseDTO refreshToken(String refreshToken);
}
