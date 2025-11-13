package com.TraceCircle.RolesManagementService.Service;

import com.TraceCircle.RolesManagementService.DTO.AuthResponseDTO;
import com.TraceCircle.RolesManagementService.DTO.ForgotPasswordRequestDTO;
import com.TraceCircle.RolesManagementService.DTO.LoginRequestDTO;
import com.TraceCircle.RolesManagementService.DTO.ResetPasswordDTO;
import com.TraceCircle.RolesManagementService.DTO.SignUpDTO;
import com.TraceCircle.RolesManagementService.DTO.SignupRequest;
import com.TraceCircle.RolesManagementService.DTO.SystemAdminOnboardingDTO;

public interface AuthManagementService {

    SystemAdminOnboardingDTO onboardSystemAdmin(SystemAdminOnboardingDTO dto);

    public SignUpDTO signup(SignupRequest req);

    AuthResponseDTO login(LoginRequestDTO request);
    
    AuthResponseDTO refreshToken(String refreshToken);
    
    void requestPasswordOtp(ForgotPasswordRequestDTO req);
    
    void resetPassword(ResetPasswordDTO req);

}
