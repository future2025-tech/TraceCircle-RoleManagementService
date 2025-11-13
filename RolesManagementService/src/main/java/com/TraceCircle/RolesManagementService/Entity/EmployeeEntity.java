package com.TraceCircle.RolesManagementService.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.TraceCircle.RolesManagementService.Constant.GenderEnum;

import jakarta.persistence.Entity;
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
@Table(name = "TC_EMPLOYEE")
public class EmployeeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;
	
	private String employeeName;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_admin_id", nullable = false)
    private SystemAdminOnboardingEntity systemAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private OrganizationEntity organization;
    
    private String employeeEmailId;
    
    private LocalDate employeeDOB;
    
    private GenderEnum employeeGender;
    
    private Long employeePhoneNumber;
    
    private String employeeLocation;
    
    private String employeeDesignation;
    
    private LocalDate employeeJoiningDate;
    
    private Boolean employeeStatus;
    
    private LocalDateTime timeStamp;
    
    @PrePersist
    void createdOn() {
    	
    	this.timeStamp = LocalDateTime.now().withNano(0);
    }
}
