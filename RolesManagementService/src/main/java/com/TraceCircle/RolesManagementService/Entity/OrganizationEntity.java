package com.TraceCircle.RolesManagementService.Entity;

import java.time.LocalDateTime;

import com.TraceCircle.RolesManagementService.Constant.BusinessTypeEnum;
import com.TraceCircle.RolesManagementService.Constant.DesignationRoleEnum;
import com.TraceCircle.RolesManagementService.Constant.IndustryEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TC_ORGANIZATION")
public class OrganizationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long organizationId;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "system_admin_id", nullable = false)
	private SystemAdminOnboardingEntity systemAdminId;
	
	private String organizationName;
	
	@Enumerated(EnumType.STRING)
	private IndustryEnum industryType;
	
	@Enumerated(EnumType.STRING)
	private BusinessTypeEnum businessType;
	
	private String organizationAddress;
	
	private String contactPerson;
	
	@Column(nullable = false, unique = true)
	private String organizationEmailId;
	
	@Enumerated(EnumType.STRING)
	private DesignationRoleEnum designation;
	
	private Long phoneNumber;
	
	private String websiteLink;
	
	private Boolean status;
	
	private LocalDateTime timeStamp;
	
	@PrePersist
	void createdOn() {
		this.timeStamp = LocalDateTime.now().withNano(0);
	}
	
}
