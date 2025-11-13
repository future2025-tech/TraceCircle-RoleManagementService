package com.TraceCircle.RolesManagementService.DTO;

import java.time.LocalDateTime;

import com.TraceCircle.RolesManagementService.Constant.BusinessTypeEnum;
import com.TraceCircle.RolesManagementService.Constant.DesignationRoleEnum;
import com.TraceCircle.RolesManagementService.Constant.IndustryEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {

	private Long organizationId;
	
	private Long systemAdminId;
	
	private String organizationName;
	
	private IndustryEnum industryType;
	
	private BusinessTypeEnum businessType;
	
	private String organizationAddress;
	
	private String contactPerson;
	
	private String organizationEmailId;
	
	private DesignationRoleEnum designation;
	
	private Long phoneNumber;
	
	private String websiteLink;
	
	private Boolean status;
	
	private LocalDateTime timeStamp;
	
}
