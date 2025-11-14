package com.TraceCircle.RolesManagementService.ServiceIMPL;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.TraceCircle.RolesManagementService.DTO.RoleManagementDTO;
import com.TraceCircle.RolesManagementService.Entity.OrganizationEntity;
import com.TraceCircle.RolesManagementService.Entity.RoleManagementEntity;
import com.TraceCircle.RolesManagementService.Entity.SystemAdminOnboardingEntity;
import com.TraceCircle.RolesManagementService.Exception.ApiException;
import com.TraceCircle.RolesManagementService.Repository.OrganizationRepository;
import com.TraceCircle.RolesManagementService.Repository.RoleManagementRepository;
import com.TraceCircle.RolesManagementService.Repository.SystemAdminOnboardingRepository;
import com.TraceCircle.RolesManagementService.Service.RoleManagementService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RoleManagementServiceIMPL implements RoleManagementService {

    private final RoleManagementRepository roleRepo;
    private final OrganizationRepository orgRepo;
    private final SystemAdminOnboardingRepository onboardingRepo;
    private final ModelMapper mapper;

    private SystemAdminOnboardingEntity getSystemAdmin() {
       
    	return onboardingRepo.findAll().stream()
                .findFirst()
                .orElseThrow(
                		() -> new ApiException("No System Admin Found"));
    }

    @Override
    public RoleManagementDTO createRole(RoleManagementDTO dto, Long organizationId) {

        log.info("Creating role {} for organization {}",
        		dto.getRoleName(), organizationId);

        OrganizationEntity org = orgRepo.findById(organizationId)
                .orElseThrow(() -> new ApiException("Organization not found"));

        if (roleRepo.existsByRoleNameAndOrganization_OrganizationId(dto.getRoleName(), organizationId)) {
           
        	throw new ApiException("Role already exists for this organization");
        }

        RoleManagementEntity entity = mapper.map(dto, RoleManagementEntity.class);
        
        entity.setOrganization(org);
        entity.setSystemAdmin(getSystemAdmin());

        RoleManagementEntity saved = roleRepo.save(entity);

        log.info("Role created successfully | id={} | name={}",
                saved.getRoleManagementId(), saved.getRoleName());

        RoleManagementDTO response = mapper.map(saved, RoleManagementDTO.class);
        
        response.setOrganizationId(org.getOrganizationId());
        
        return response;
    }

    @Override
    public RoleManagementDTO roleById(Long id) {
       
    	log.info("Fetching role by id={}", id);

        RoleManagementEntity entity = roleRepo.findById(id)
                .orElseThrow(() -> new ApiException("Role not found"));

        return mapper.map(entity, RoleManagementDTO.class);
    }

    @Override
    public List<RoleManagementDTO> allRoles(Long organizationId) {

        log.info("Fetching all roles for organization id={}", organizationId);

        return roleRepo.findByOrganization_OrganizationId(organizationId)
                .stream()
                .map(role -> mapper.map(role, RoleManagementDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleManagementDTO updateRole(RoleManagementDTO dto) {

        log.info("Updating role {}", dto.getRoleManagementId());

        RoleManagementEntity entity = roleRepo.findById(dto.getRoleManagementId())
                .orElseThrow(() -> new ApiException("Role not found"));

        mapper.map(dto, entity);

        RoleManagementEntity updated = roleRepo.save(entity);

        return mapper.map(updated, RoleManagementDTO.class);
    }

    @Override
    public void deleteRole(Long id) {

        log.warn("Deleting role id={}", id);

        if (!roleRepo.existsById(id)) {
           
        	throw new ApiException("Role not found");
        }

        roleRepo.deleteById(id);

        log.info("Role deleted successfully id={}", id);
    }
}
