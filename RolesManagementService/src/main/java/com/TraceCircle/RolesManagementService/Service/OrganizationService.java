package com.TraceCircle.RolesManagementService.Service;

import java.util.List;

import com.TraceCircle.RolesManagementService.DTO.AuthResponseDTO;
import com.TraceCircle.RolesManagementService.DTO.CreateLoginDTO;
import com.TraceCircle.RolesManagementService.DTO.LoginRequestDTO;
import com.TraceCircle.RolesManagementService.DTO.OrganizationDTO;
import com.TraceCircle.RolesManagementService.DTO.SetPasswordDTO;

public interface OrganizationService {

	OrganizationDTO createOrganization(OrganizationDTO orgDTO);
	
	OrganizationDTO organizationById(Long id);
	
	List<OrganizationDTO> getAllOrganizations();
	
	OrganizationDTO editOrganization(OrganizationDTO orgDTO);
	
	void delete(Long id);
	
	public void createLoginForOrganization(CreateLoginDTO dto);
	
	public void setPassword(SetPasswordDTO dto);
	
	public AuthResponseDTO loginOrganization(LoginRequestDTO dto);
	
	
}
