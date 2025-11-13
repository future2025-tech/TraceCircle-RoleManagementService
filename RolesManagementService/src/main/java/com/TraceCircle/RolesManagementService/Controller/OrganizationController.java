package com.TraceCircle.RolesManagementService.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TraceCircle.RolesManagementService.DTO.AuthResponseDTO;
import com.TraceCircle.RolesManagementService.DTO.CreateLoginDTO;
import com.TraceCircle.RolesManagementService.DTO.LoginRequestDTO;
import com.TraceCircle.RolesManagementService.DTO.OrganizationDTO;
import com.TraceCircle.RolesManagementService.DTO.SetPasswordDTO;
import com.TraceCircle.RolesManagementService.Service.OrganizationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/organization")
@RequiredArgsConstructor
public class OrganizationController {

	private final OrganizationService service;

    @PostMapping("/create")
    public ResponseEntity<OrganizationDTO> create(@RequestBody OrganizationDTO dto) {
       
    	return ResponseEntity.ok(service.createOrganization(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> get(@PathVariable Long id) {
        
    	return ResponseEntity.ok(service.organizationById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrganizationDTO>> all() {
        
    	return ResponseEntity.ok(service.getAllOrganizations());
    }

    @PutMapping("/update")
    public ResponseEntity<OrganizationDTO> update(@RequestBody OrganizationDTO dto) {
        
    	return ResponseEntity.ok(service.editOrganization(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        
    	service.delete(id);
        
    	return ResponseEntity.ok("Organization deleted successfully");
    }
    
    @PostMapping("/create-login")
    public ResponseEntity<?> createLogin(@RequestBody CreateLoginDTO dto) {
       
    	service.createLoginForOrganization(dto);
        
    	return ResponseEntity.ok("Activation link sent to organization email");
    }

    @PostMapping("/set-password")
    public ResponseEntity<?> setPassword(@RequestBody SetPasswordDTO dto) {
        
    	service.setPassword(dto);
        
    	return ResponseEntity.ok("Password set successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO dto) {
       
    	return ResponseEntity.ok(service.loginOrganization(dto));
    }
}
