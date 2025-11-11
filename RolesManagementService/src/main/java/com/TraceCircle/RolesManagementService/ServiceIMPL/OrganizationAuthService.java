package com.TraceCircle.RolesManagementService.ServiceIMPL;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.TraceCircle.RolesManagementService.DTO.AuthResponseDTO;
import com.TraceCircle.RolesManagementService.DTO.OrganizationLoginRequestDTO;
import com.TraceCircle.RolesManagementService.Entity.OrganizationUserEntity;
import com.TraceCircle.RolesManagementService.Exception.ApiException;
import com.TraceCircle.RolesManagementService.Repository.OrganizationUserRepository;
import com.TraceCircle.RolesManagementService.Security.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationAuthService {

    private final OrganizationUserRepository orgUserRepo;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;

    public AuthResponseDTO loginOrganization(OrganizationLoginRequestDTO dto) {

        OrganizationUserEntity user = orgUserRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ApiException("Invalid credentials"));

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		dto.getEmail(), dto.getPassword()));

        String access = jwtUtil.generateAccessToken(dto.getEmail());
        String refresh = jwtUtil.generateRefreshToken(dto.getEmail());

        log.info("Organization Login Successful | email={}", dto.getEmail());

        return new AuthResponseDTO(access, refresh, "Bearer");
    }
}
