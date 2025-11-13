package com.TraceCircle.RolesManagementService.ServiceIMPL;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TraceCircle.RolesManagementService.DTO.AuthResponseDTO;
import com.TraceCircle.RolesManagementService.DTO.CreateLoginDTO;
import com.TraceCircle.RolesManagementService.DTO.LoginRequestDTO;
import com.TraceCircle.RolesManagementService.DTO.OrganizationDTO;
import com.TraceCircle.RolesManagementService.DTO.SetPasswordDTO;
import com.TraceCircle.RolesManagementService.Entity.OrganizationEntity;
import com.TraceCircle.RolesManagementService.Entity.OrganizationLoginToken;
import com.TraceCircle.RolesManagementService.Entity.OrganizationUserEntity;
import com.TraceCircle.RolesManagementService.Entity.SystemAdminOnboardingEntity;
import com.TraceCircle.RolesManagementService.Exception.ApiException;
import com.TraceCircle.RolesManagementService.Repository.OrganizationLoginTokenRepository;
import com.TraceCircle.RolesManagementService.Repository.OrganizationRepository;
import com.TraceCircle.RolesManagementService.Repository.OrganizationUserRepository;
import com.TraceCircle.RolesManagementService.Repository.SystemAdminOnboardingRepository;
import com.TraceCircle.RolesManagementService.Security.EmailUtil;
import com.TraceCircle.RolesManagementService.Security.JWTUtil;
import com.TraceCircle.RolesManagementService.Service.OrganizationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrganizationServiceIMPL implements OrganizationService {

    private final OrganizationRepository orgRepo;
    private final SystemAdminOnboardingRepository onboardingRepo;
    private final OrganizationUserRepository orgUserRepo;
    private final OrganizationLoginTokenRepository tokenRepo;
    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder encoder;
    private final EmailUtil emailService; 
    private final ModelMapper mapper;

    @Override
    public OrganizationDTO createOrganization(OrganizationDTO orgDTO) {

        log.info("Creating organization for systemAdminId={}"
        		, orgDTO.getSystemAdminId());

        SystemAdminOnboardingEntity systemAdmin = onboardingRepo.findById(
        		orgDTO.getSystemAdminId())
                .orElseThrow(() -> {
                    
                	log.error("System Admin not found | id={}"
                			, orgDTO.getSystemAdminId());
                    
                	return new ApiException("System Admin not found");
                	});

        OrganizationEntity entity = mapper.map(orgDTO, OrganizationEntity.class);

        entity.setSystemAdminId(systemAdmin);

        OrganizationEntity saved = orgRepo.save(entity);

        log.info("Organization created successfully | id={} | organizationName={}",
                saved.getOrganizationId(), saved.getOrganizationName());

        OrganizationDTO response = mapper.map(saved, OrganizationDTO.class);
        response.setSystemAdminId(systemAdmin.getId()); 
        
        return response;
    }

    @Override
    public OrganizationDTO organizationById(Long id) {
        
        log.info("Fetching organization by id={}", id);

        OrganizationEntity entity = orgRepo.findById(id)
                .orElseThrow(() -> {
                    
                	log.error("Organization not found | id={}", id);
                   
                	return new ApiException("Organization not found with id: " + id);
                });

        return mapper.map(entity, OrganizationDTO.class);
    }

    @Override
    public List<OrganizationDTO> getAllOrganizations() {

        log.info("Fetching all organizations");

        List<OrganizationEntity> list = orgRepo.findAll();

        return list.stream()
                .map(entity -> mapper.map(entity, OrganizationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrganizationDTO editOrganization(OrganizationDTO orgDTO) {

        log.info("Updating organization id={}", orgDTO.getOrganizationId());

        OrganizationEntity existing = orgRepo.findById(orgDTO.getOrganizationId())
                .orElseThrow(() -> {
                    
                	log.error("Organization not found | id={}"
                    		, orgDTO.getOrganizationId());
                    
                    return new ApiException("Organization not found with id: " 
                    		+ orgDTO.getOrganizationId());
                });

        mapper.map(orgDTO, existing); 

        OrganizationEntity updated = orgRepo.save(existing);

        log.info("Organization updated successfully | id={} | name={}"
        		, updated.getOrganizationId(), updated.getOrganizationName());

        return mapper.map(updated, OrganizationDTO.class);
    }

    @Override
    public void delete(Long id) {

        log.warn("Deleting organization id={}", id);

        if (!orgRepo.existsById(id)) {
            
        	log.error("Delete failed: Organization does not exist | id={}", id);
            
        	throw new ApiException("Organization not found with id: " + id);
        }

        orgRepo.deleteById(id);

        log.info("Organization deleted successfully | id={}", id);
    }
    
    
    public void createLoginForOrganization(CreateLoginDTO dto) {

        log.info("Creating login for Organization ID={}", dto.getId());

        OrganizationEntity org = orgRepo.findById(dto.getId())
                .orElseThrow(() -> new ApiException("Organization not found"));

        if (!Boolean.TRUE.equals(org.getStatus())) {
            
        	log.warn("Organization login creation blocked: inactive orgId={}"
            		, dto.getId());
           
            throw new ApiException("Organization must be active to create login");
        }

        if (orgUserRepo.existsByEmail(org.getOrganizationEmailId())) {
            
        	log.warn("Login already exists for organization email={}"
        			, org.getOrganizationEmailId());
            
        	throw new ApiException("Login already exists for this organization");
        }

        OrganizationUserEntity user = orgUserRepo.save(
                new OrganizationUserEntity(null, org, 
                		org.getOrganizationEmailId(), null, false, null)
        );

        String token = UUID.randomUUID().toString();
        
        tokenRepo.save(new OrganizationLoginToken(null, user, token, null));

        String link = "http://localhost:5173/org/set-password?token=" + token;

        emailService.sendEmail(
                org.getOrganizationEmailId(),
                "Activate Your Organization Account - TraceCircle",
                "Hello " + org.getOrganizationName() +
                        ",\n\nClick the link below to set your password:\n\n" +link + 
                        "\n\nThis link is valid for 24 hours.\n\n- TraceCircle Team"
        );

        log.info("Organization login activation link sent to {}"
        		, org.getOrganizationEmailId());
    }


    public void setPassword(SetPasswordDTO dto) {

        OrganizationLoginToken t = tokenRepo.findByToken(dto.getToken())
                .orElseThrow(() -> new ApiException("Invalid or expired link"));

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            
        	throw new ApiException("Passwords do not match");
        }

        OrganizationUserEntity user = t.getOrgUser();
        user.setPasswordHash(encoder.encode(dto.getPassword()));
        user.setActive(true);

        orgUserRepo.save(user);
        tokenRepo.delete(t);

        log.info("Password set successfully for organization login {}"
        		, user.getEmail());
    }

    public AuthResponseDTO loginOrganization(LoginRequestDTO dto) {

        OrganizationUserEntity user = orgUserRepo.findByEmail(dto.getEmailId())
                .orElseThrow(() -> new ApiException("Invalid credentials"));

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		dto.getEmailId(), dto.getPassword()));

        String access = jwtUtil.generateAccessToken(dto.getEmailId());
        String refresh = jwtUtil.generateRefreshToken(dto.getEmailId());

        log.info("Organization Login Successful | email={}", dto.getEmailId());

        return new AuthResponseDTO(access, refresh, "Bearer");
    }
}
