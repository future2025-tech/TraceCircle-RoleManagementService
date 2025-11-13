package com.TraceCircle.RolesManagementService.ServiceIMPL;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TraceCircle.RolesManagementService.DTO.DepartmentDTO;
import com.TraceCircle.RolesManagementService.Entity.DepartmentEntity;
import com.TraceCircle.RolesManagementService.Entity.OrganizationEntity;
import com.TraceCircle.RolesManagementService.Entity.SystemAdminOnboardingEntity;
import com.TraceCircle.RolesManagementService.Exception.ApiException;
import com.TraceCircle.RolesManagementService.Repository.DepartmentRepository;
import com.TraceCircle.RolesManagementService.Repository.OrganizationRepository;
import com.TraceCircle.RolesManagementService.Repository.SystemAdminOnboardingRepository;
import com.TraceCircle.RolesManagementService.Service.DepartmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceIMPL implements DepartmentService {

    private final DepartmentRepository departmentRepo;
    private final OrganizationRepository organizationRepo;
    private final SystemAdminOnboardingRepository adminRepo;
    private final ModelMapper mapper;

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO dto) {
       
    	log.info("Creating department: {} under organizationId={}"
    			, dto.getDepartmentName(), dto.getOrganizationId());

        OrganizationEntity org = organizationRepo.findById(dto.getOrganizationId())
                .orElseThrow(() -> {
                    
                	log.error("Organization not found | id={}"
                			, dto.getOrganizationId());
                    
                	return new ApiException("Organization not found with id: "
                	+ dto.getOrganizationId());
                });

        SystemAdminOnboardingEntity admin = adminRepo.findById(
        		dto.getSystemAdminId()).orElseThrow(() -> {
                    
                	log.error("System admin not found | id={}"
                			, dto.getSystemAdminId());
                    
                	return new ApiException("System admin not found with id: " 
                	+ dto.getSystemAdminId());
                });

        if (departmentRepo.existsByDepartmentNameAndOrganization_OrganizationId(
        		dto.getDepartmentName(), dto.getOrganizationId())) {
        	
            log.warn("Duplicate department found: {} in organizationId={}"
            		, dto.getDepartmentName(), dto.getOrganizationId());
            
            throw new ApiException("Department already exists in this organization");
        }

        DepartmentEntity entity = mapper.map(dto, DepartmentEntity.class);
       
        entity.setSystemAdmin(admin);
        entity.setOrganization(org);

        DepartmentEntity saved = departmentRepo.save(entity);
        
        log.info("Department created successfully | id={} | name={}"
        		, saved.getDepartmentId(), saved.getDepartmentName());

        DepartmentDTO response = mapper.map(saved, DepartmentDTO.class);
       
        response.setOrganizationName(org.getOrganizationName());
        response.setOrganizationId(org.getOrganizationId());
        response.setSystemAdminId(admin.getId());

        return response;
    }

    @Override
    public DepartmentDTO departmentById(Long id) {
       
    	log.info("Fetching department by id={}", id);

        DepartmentEntity entity = departmentRepo.findById(id)
                .orElseThrow(() -> {
                    
                	log.error("Department not found | id={}", id);
                    
                	return new ApiException("Department not found with id: " + id);
                });

        DepartmentDTO dto = mapper.map(entity, DepartmentDTO.class);
       
        dto.setOrganizationName(entity.getOrganization().getOrganizationName());
        dto.setOrganizationId(entity.getOrganization().getOrganizationId());
        dto.setSystemAdminId(entity.getSystemAdmin().getId());

        return dto;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        
    	log.info("Fetching all departments");

        List<DepartmentEntity> list = departmentRepo.findAll();

        return list.stream().map(entity -> {
           
        	DepartmentDTO dto = mapper.map(entity, DepartmentDTO.class);
           
        	dto.setOrganizationName(entity.getOrganization().getOrganizationName());
            dto.setOrganizationId(entity.getOrganization().getOrganizationId());
            dto.setSystemAdminId(entity.getSystemAdmin().getId());
            
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO editDepartment(DepartmentDTO dto) {
       
    	log.info("Updating department id={}", dto.getDepartmentId());

        DepartmentEntity existing = departmentRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> {
                    
                	log.error("Department not found | id={}", dto.getDepartmentId());
                    
                	return new ApiException("Department not found with id: " 
                	+ dto.getDepartmentId());
                });

        mapper.map(dto, existing);

        DepartmentEntity updated = departmentRepo.save(existing);
        
        log.info("Department updated successfully | id={} | name={}"
        		, updated.getDepartmentId(), updated.getDepartmentName());

        DepartmentDTO response = mapper.map(updated, DepartmentDTO.class);
        
        response.setOrganizationName(updated.getOrganization().getOrganizationName());
        response.setSystemAdminId(updated.getSystemAdmin().getId());

        return response;
    }

    @Override
    public void delete(Long id) {
        
    	log.warn("Deleting department id={}", id);

        if (!departmentRepo.existsById(id)) {
            
        	log.error("Delete failed: Department does not exist | id={}", id);
            
        	throw new ApiException("Department not found with id: " + id);
        }

        departmentRepo.deleteById(id);
       
        log.info("Department deleted successfully | id={}", id);
    }
}
