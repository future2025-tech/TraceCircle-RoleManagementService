package com.TraceCircle.RolesManagementService.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TraceCircle.RolesManagementService.DTO.SystemAdminOnboardingDTO;
import com.TraceCircle.RolesManagementService.ServiceIMPL.SystemAdminOnboardingServiceIMPL;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/systemAdminOnboarding")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SystemAdminOnboardingController {

	private final SystemAdminOnboardingServiceIMPL systemAdminService;
	
	@PostMapping
	public void systemAdminOnboarding(@RequestBody SystemAdminOnboardingDTO systemAdminDTO) {
		
		systemAdminService.create();
	}
}
