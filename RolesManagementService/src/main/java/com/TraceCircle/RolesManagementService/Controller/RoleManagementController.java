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

import com.TraceCircle.RolesManagementService.DTO.RoleManagementDTO;
import com.TraceCircle.RolesManagementService.Service.RoleManagementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth/roles")
@RequiredArgsConstructor
@Slf4j
public class RoleManagementController {

    private final RoleManagementService roleService;

    @PostMapping("/{organizationId}/create")
    public ResponseEntity<RoleManagementDTO> createRole(
            @PathVariable Long organizationId,
            @RequestBody RoleManagementDTO dto) {

        log.info("API: Create role for org {}", organizationId);

        return ResponseEntity.ok(roleService.createRole(dto, organizationId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleManagementDTO> getRole(@PathVariable Long id) {
       
    	return ResponseEntity.ok(roleService.roleById(id));
    }

    @GetMapping("/list/{organizationId}")
    public ResponseEntity<List<RoleManagementDTO>> getAllRoles(
            @PathVariable Long organizationId) {

        return ResponseEntity.ok(roleService.allRoles(organizationId));
    }

    @PutMapping("/update")
    public ResponseEntity<RoleManagementDTO> updateRole(
            @RequestBody RoleManagementDTO dto) {

        return ResponseEntity.ok(roleService.updateRole(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {

        roleService.deleteRole(id);
       
        return ResponseEntity.ok("Role deleted successfully");
    }
}
