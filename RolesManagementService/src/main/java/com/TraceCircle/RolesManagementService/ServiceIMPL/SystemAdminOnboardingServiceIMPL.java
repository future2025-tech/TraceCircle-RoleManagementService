package com.TraceCircle.RolesManagementService.ServiceIMPL;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.TraceCircle.RolesManagementService.DTO.SystemAdminOnboardingDTO;
import com.TraceCircle.RolesManagementService.Entity.SystemAdminOnboardingEntity;
import com.TraceCircle.RolesManagementService.Repository.SystemAdminOnboardingRepository;
import com.TraceCircle.RolesManagementService.Service.SystemAdminOnboardingService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SystemAdminOnboardingServiceIMPL implements SystemAdminOnboardingService{
	
	private final ModelMapper modelMapper;
	
	private final SystemAdminOnboardingRepository systemAdminRepo;

	@Override
	public SystemAdminOnboardingDTO create() {

		SystemAdminOnboardingEntity entity = modelMapper.map(modelMapper, SystemAdminOnboardingEntity.class);
		
		SystemAdminOnboardingEntity savedEntity = systemAdminRepo.save(entity);
		
		return modelMapper.map(savedEntity, SystemAdminOnboardingDTO.class);
	}

	
}
