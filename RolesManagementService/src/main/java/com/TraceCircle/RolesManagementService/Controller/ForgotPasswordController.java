package com.TraceCircle.RolesManagementService.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.TraceCircle.RolesManagementService.DTO.ForgotPasswordRequestDTO;
import com.TraceCircle.RolesManagementService.DTO.ResetPasswordDTO;
import com.TraceCircle.RolesManagementService.ServiceIMPL.AuthManagementServiceIMPL;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth/forgot")
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordController {

    private final AuthManagementServiceIMPL authService;

    @PostMapping("/request-otp")
    public ResponseEntity<String> requestOtp(
    		@Valid @RequestBody ForgotPasswordRequestDTO req) {

        log.info("Forgot Password - OTP Request Initiated | email={}"
        		, req.getEmailId());

        try {
            authService.requestPasswordOtp(req);
            
            log.info("OTP Sent Successfully | email={}", req.getEmailId());
            
            return ResponseEntity.ok("OTP sent to your email");
        } 
        catch (Exception e) {
            
        	log.error("OTP Request Failed | email={} | reason={}"
        			, req.getEmailId(), e.getMessage());
            
        	return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
    		@Valid @RequestBody ResetPasswordDTO req) {

        log.info("Password Reset Attempt | email={}", req.getEmailId());

        try {
            authService.resetPassword(req);
           
            log.info("Password Reset Successful | email={}", req.getEmailId());
           
            return ResponseEntity.ok("Password updated successfully");
        } 
        catch (Exception e) {
            
        	log.error("Password Reset Failed | email={} | reason={}"
            		, req.getEmailId(), e.getMessage());
           
        	return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
