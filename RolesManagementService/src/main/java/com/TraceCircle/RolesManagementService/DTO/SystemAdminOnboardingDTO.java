package com.TraceCircle.RolesManagementService.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemAdminOnboardingDTO {

	private Long id;
	
	private String systemName;
	
	private String systemAddress;
	
	private String systemRegion;
	
	private String systemCity;
	
	private Long systemPostalcode;
	
	private String systemEmailId;
	
	private Long systemPhoneNumber;
	
	private LocalDateTime createdAt;
}
