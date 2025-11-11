package com.TraceCircle.RolesManagementService.ServiceIMPL;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TraceCircle.RolesManagementService.Entity.SignUpEntity;
import com.TraceCircle.RolesManagementService.Exception.ApiException;
import com.TraceCircle.RolesManagementService.Repository.SignUpRepository;
import com.TraceCircle.RolesManagementService.Security.PasswordValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetService {

    private final SignUpRepository signUpRepo;
    private final PasswordEncoder encoder;

    public String requestOtp(String emailId) {
        
    	SignUpEntity user = signUpRepo.findByEmailId(emailId)
                .orElseThrow(() -> new ApiException("Email not registered"));

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000); 
        user.setResetOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        signUpRepo.save(user);

        log.info("OTP generated for {} -> {}", emailId, otp);

        return "OTP sent to registered email";
    }

    public String verifyOtp(String emailId, String otp) {
        SignUpEntity user = signUpRepo.findByEmailId(emailId)
                .orElseThrow(() -> new ApiException("Email not registered"));

        if (user.getResetOtp() == null || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new ApiException("OTP expired, request again");
        }

        if (!user.getResetOtp().equals(otp)) {
            throw new ApiException("Invalid OTP");
        }

        return "OTP verified";
    }

    public String resetPassword(String emailId, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new ApiException("Passwords do not match");
        }

        if (!PasswordValidator.isStrong(newPassword)) {
            throw new ApiException("Weak password");
        }

        SignUpEntity user = signUpRepo.findByEmailId(emailId)
                .orElseThrow(() -> new ApiException("Email not registered"));

        user.setPasswordHash(encoder.encode(newPassword));
        user.setResetOtp(null);
        user.setOtpExpiry(null);
        signUpRepo.save(user);

        return "Password reset successful";
    }
}
