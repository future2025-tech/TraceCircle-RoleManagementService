package com.TraceCircle.RolesManagementService.DTO;

import lombok.Data;

@Data
public class SetPasswordDTO {
    
	private String token;
    
	private String password;
    
	private String confirmPassword;
}

