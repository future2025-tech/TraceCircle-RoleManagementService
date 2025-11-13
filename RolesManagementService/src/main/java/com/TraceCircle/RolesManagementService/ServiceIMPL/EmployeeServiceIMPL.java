package com.TraceCircle.RolesManagementService.ServiceIMPL;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.TraceCircle.RolesManagementService.DTO.EmployeeDTO;
import com.TraceCircle.RolesManagementService.Entity.EmployeeEntity;
import com.TraceCircle.RolesManagementService.Entity.OrganizationEntity;
import com.TraceCircle.RolesManagementService.Entity.SystemAdminOnboardingEntity;
import com.TraceCircle.RolesManagementService.Exception.ApiException;
import com.TraceCircle.RolesManagementService.Repository.EmployeeRepository;
import com.TraceCircle.RolesManagementService.Repository.OrganizationRepository;
import com.TraceCircle.RolesManagementService.Repository.SystemAdminOnboardingRepository;
import com.TraceCircle.RolesManagementService.Service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceIMPL implements EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepo;
    private final SystemAdminOnboardingRepository systemAdminRepo;
    private final OrganizationRepository organizationRepo;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO empDTO) {

        log.info("Creating employee {}", empDTO.getEmployeeName());

        SystemAdminOnboardingEntity sysAdmin = systemAdminRepo.
        		findById(empDTO.getSystemAdminId()).orElseThrow(
        				() -> new ApiException("System admin not found"));

        OrganizationEntity org = organizationRepo.
        		findById(empDTO.getOrganizationId()).orElseThrow(
        				() -> new ApiException("Organization not found"));

        EmployeeEntity entity = modelMapper.map(empDTO, EmployeeEntity.class);
        entity.setSystemAdmin(sysAdmin);
        entity.setOrganization(org);

        EmployeeEntity saved = employeeRepo.save(entity);

        log.info("Employee created successfully | id={} | name={}",
                saved.getEmployeeId(), saved.getEmployeeName());

        EmployeeDTO response = modelMapper.map(saved, EmployeeDTO.class);
        response.setSystemAdminId(sysAdmin.getId());
        response.setOrganizationId(org.getOrganizationId());

        return response;
    }

    @Override
    public EmployeeDTO employeeById(Long id) {

        log.info("Fetching employee id={}", id);

        EmployeeEntity emp = employeeRepo.findById(id)
                .orElseThrow(() -> new ApiException("Employee not found"));

        EmployeeDTO dto = modelMapper.map(emp, EmployeeDTO.class);
        dto.setSystemAdminId(emp.getSystemAdmin().getId());
        dto.setOrganizationId(emp.getOrganization().getOrganizationId());

        return dto;
    }

    @Override
    public List<EmployeeDTO> listOfEmployees() {

        log.info("Fetching list of all employees");

        return employeeRepo.findAll()
                .stream().map(emp -> {
                    EmployeeDTO dto = modelMapper.map(emp, EmployeeDTO.class);
                    dto.setSystemAdminId(emp.getSystemAdmin().getId());
                    dto.setOrganizationId(emp.getOrganization().getOrganizationId());
                   
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO editEmployee(EmployeeDTO empDTO) {

        log.info("Updating employee id={}", empDTO.getEmployeeId());

        EmployeeEntity existing = employeeRepo.findById(empDTO.getEmployeeId())
                .orElseThrow(() -> new ApiException("Employee not found"));

        SystemAdminOnboardingEntity sysAdmin = systemAdminRepo.
        		findById(empDTO.getSystemAdminId()).orElseThrow(
        				() -> new ApiException("System admin not found"));

        OrganizationEntity org = organizationRepo.
        		findById(empDTO.getOrganizationId()).orElseThrow(
        				() -> new ApiException("Organization not found"));

        modelMapper.map(empDTO, existing);
        existing.setSystemAdmin(sysAdmin);
        existing.setOrganization(org);

        EmployeeEntity updated = employeeRepo.save(existing);

        log.info("Employee updated | id={} | name={}",
                updated.getEmployeeId(), updated.getEmployeeName());

        EmployeeDTO response = modelMapper.map(updated, EmployeeDTO.class);
        response.setSystemAdminId(sysAdmin.getId());
        response.setOrganizationId(org.getOrganizationId());

        return response;
    }

    @Override
    public void deleteEmployee(Long id) {

        log.warn("Deleting employee id={}", id);

        if (!employeeRepo.existsById(id)) {
           
        	log.error("Delete failed. Employee not found | id={}", id);
            
        	throw new ApiException("Employee not found with id: " + id);
        }

        employeeRepo.deleteById(id);

        log.info("Employee deleted successfully | id={}", id);
    }
}
