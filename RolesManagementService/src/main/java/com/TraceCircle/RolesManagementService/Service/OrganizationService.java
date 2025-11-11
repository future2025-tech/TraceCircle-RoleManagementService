package com.TraceCircle.RolesManagementService.Service;

import java.util.List;

import com.TraceCircle.RolesManagementService.DTO.AuthResponseDTO;
import com.TraceCircle.RolesManagementService.DTO.CreateOrganizationLoginDTO;
import com.TraceCircle.RolesManagementService.DTO.OrganizationDTO;
import com.TraceCircle.RolesManagementService.DTO.OrganizationLoginRequestDTO;
import com.TraceCircle.RolesManagementService.DTO.OrganizationSetPasswordDTO;

public interface OrganizationService {

	OrganizationDTO createOrganization(OrganizationDTO orgDTO);
	
	OrganizationDTO organizationById(Long id);
	
	List<OrganizationDTO> getAllOrganizations();
	
	OrganizationDTO editOrganization(OrganizationDTO orgDTO);
	
	void delete(Long id);
	
	public void createLoginForOrganization(CreateOrganizationLoginDTO dto);
	
	public void setPassword(OrganizationSetPasswordDTO dto);
	
	public AuthResponseDTO login(OrganizationLoginRequestDTO dto);
	
	
}
