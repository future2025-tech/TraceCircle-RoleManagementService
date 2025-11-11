package com.TraceCircle.RolesManagementService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifyOtpRequestDTO {
   
	private String emailId;
    
	private String otp;
}

