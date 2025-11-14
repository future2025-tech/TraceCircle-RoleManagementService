package com.TraceCircle.RolesManagementService.Service;

import java.util.List;

import com.TraceCircle.RolesManagementService.DTO.RoleManagementDTO;

public interface RoleManagementService {

    RoleManagementDTO createRole(RoleManagementDTO dto, Long organizationId);

    RoleManagementDTO roleById(Long id);

    List<RoleManagementDTO> allRoles(Long organizationId);

    RoleManagementDTO updateRole(RoleManagementDTO dto);

    void deleteRole(Long id);
}
