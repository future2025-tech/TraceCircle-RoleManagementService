package com.TraceCircle.RolesManagementService.DTO;

import lombok.Data;

@Data
public class OrganizationSetPasswordDTO {
    
	private String token;
    
	private String password;
    
	private String confirmPassword;
}

