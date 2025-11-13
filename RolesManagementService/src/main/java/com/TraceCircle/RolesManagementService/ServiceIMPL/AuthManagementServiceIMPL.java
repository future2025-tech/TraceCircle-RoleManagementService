package com.TraceCircle.RolesManagementService.ServiceIMPL;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TraceCircle.RolesManagementService.DTO.AuthResponseDTO;
import com.TraceCircle.RolesManagementService.DTO.ForgotPasswordRequestDTO;
import com.TraceCircle.RolesManagementService.DTO.LoginRequestDTO;
import com.TraceCircle.RolesManagementService.DTO.ResetPasswordDTO;
import com.TraceCircle.RolesManagementService.DTO.SignUpDTO;
import com.TraceCircle.RolesManagementService.DTO.SignupRequest;
import com.TraceCircle.RolesManagementService.DTO.SystemAdminOnboardingDTO;
import com.TraceCircle.RolesManagementService.Entity.OtpEntity;
import com.TraceCircle.RolesManagementService.Entity.SignUpEntity;
import com.TraceCircle.RolesManagementService.Entity.SystemAdminOnboardingEntity;
import com.TraceCircle.RolesManagementService.Exception.ApiException;
import com.TraceCircle.RolesManagementService.Repository.OtpRepository;
import com.TraceCircle.RolesManagementService.Repository.SignUpRepository;
import com.TraceCircle.RolesManagementService.Repository.SystemAdminOnboardingRepository;
import com.TraceCircle.RolesManagementService.Security.EmailUtil;
import com.TraceCircle.RolesManagementService.Security.JWTUtil;
import com.TraceCircle.RolesManagementService.Security.PasswordValidator;
import com.TraceCircle.RolesManagementService.Service.AuthManagementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthManagementServiceIMPL implements AuthManagementService {

    private final SystemAdminOnboardingRepository onboardingRepo;
    private final OtpRepository otpRepo;
    private final SignUpRepository signUpRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final EmailUtil emailUtil;
    private final JWTUtil jwtUtil;
    private final ModelMapper mapper;

    @Override
    public SystemAdminOnboardingDTO onboardSystemAdmin(SystemAdminOnboardingDTO dto) {
        
    	SystemAdminOnboardingEntity entity = mapper.map(
    			dto, SystemAdminOnboardingEntity.class);
       
    	SystemAdminOnboardingEntity saved = onboardingRepo.save(entity);
        
    	log.info("System Admin onboarded: id={}", saved.getId());
       
    	return mapper.map(saved, SystemAdminOnboardingDTO.class);
    }

    @Override
    @Transactional
    public SignUpDTO signup(SignupRequest req) {

        log.debug("Validating signup request");

        // 1. Get the latest system admin onboarding record
        SystemAdminOnboardingEntity systemAdmin = onboardingRepo
                .findTopByOrderByIdDesc()
                .orElseThrow(() -> new ApiException("No onboarding record found"));

        String emailFromOnboarding = systemAdmin.getSystemEmailId();

        log.info("System Admin found id={} email={}", systemAdmin.getId(), emailFromOnboarding);

        // 2. Check passwords match
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new ApiException("Passwords do not match");
        }

        // 3. Validate password strength
        if (!PasswordValidator.isStrong(req.getPassword())) {
            throw new ApiException("Password does not meet strength requirements");
        }

        // 4. Prevent duplicate registration
        if (signUpRepo.existsByEmailId(emailFromOnboarding)) {
            throw new ApiException("Email already registered");
        }

        // 5. Create new signup user
        SignUpEntity user = new SignUpEntity();
        user.setSystemAdmin(systemAdmin);
        user.setEmailId(emailFromOnboarding);
        user.setPasswordHash(encoder.encode(req.getPassword()));
        user.setRole(req.getRole());

        SignUpEntity saved = signUpRepo.save(user);

        log.info("User created successfully | userId={} | email={}", saved.getId(), saved.getEmailId());

        return mapper.map(saved, SignUpDTO.class);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO req) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmailId(),
                		req.getPassword()));

        String accessToken = jwtUtil.generateAccessToken(req.getEmailId());
        String refreshToken = jwtUtil.generateRefreshToken(req.getEmailId());

        log.info("Login successful: {}", req.getEmailId());

        return new AuthResponseDTO(accessToken, refreshToken, "Bearer");
    }
    
    @Override
    public AuthResponseDTO refreshToken(String refreshToken) {
       
    	String email = jwtUtil.extractUsername(refreshToken);

        if (!jwtUtil.isValidToken(refreshToken, email)) {
            throw new ApiException("Invalid or expired refresh token");
        }

        String newAccess = jwtUtil.generateAccessToken(email);
        String newRefresh = jwtUtil.generateRefreshToken(email);

        log.info("Token refreshed for {}", email);

        return new AuthResponseDTO(newAccess, newRefresh, "Bearer");
    }
    
    @Override
    public void requestPasswordOtp(ForgotPasswordRequestDTO req) {

        SignUpEntity user = signUpRepo.findByEmailId(req.getEmailId())
                .orElseThrow(() -> new ApiException("Email not registered"));

        String otp = String.format("%06d", new java.util.Random().nextInt(1000000));

        OtpEntity otpEntity = new OtpEntity(null, req.getEmailId(), otp,
                LocalDateTime.now().plusMinutes(10), false);

        otpRepo.save(otpEntity);

        log.info("OTP generated for {} | OTP={}", req.getEmailId(), otp);

        emailUtil.sendOtpEmail(req.getEmailId(), otp);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordDTO req) {

        if (!req.getNewPassword().equals(req.getConfirmPassword()))
            throw new ApiException("Passwords do not match");

        PasswordValidator.validateOrThrow(req.getNewPassword());

        OtpEntity otpRecord = otpRepo.findTopByEmailIdAndUsedFalseOrderByIdDesc(req.getEmailId())
                .orElseThrow(() -> new ApiException("No OTP found or expired"));

        if (!otpRecord.getOtp().equals(req.getOtp()))
            throw new ApiException("Invalid OTP");

        if (otpRecord.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new ApiException("OTP expired, request again");

        SignUpEntity user = signUpRepo.findByEmailId(req.getEmailId())
                .orElseThrow(() -> new ApiException("User not found"));

        user.setPasswordHash(encoder.encode(req.getNewPassword()));
        signUpRepo.save(user);

        otpRecord.setUsed(true);
        otpRepo.save(otpRecord);

        emailUtil.sendPasswordResetConfirmation(req.getEmailId());

        log.info("Password updated successfully for {}", req.getEmailId());
    }
}
