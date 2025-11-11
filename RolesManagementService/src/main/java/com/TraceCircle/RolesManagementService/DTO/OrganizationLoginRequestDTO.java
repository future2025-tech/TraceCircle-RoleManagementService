package com.TraceCircle.RolesManagementService.DTO;

import lombok.Data;

@Data
public class OrganizationLoginRequestDTO {
	
    private String email;
    
    private String password;
}
