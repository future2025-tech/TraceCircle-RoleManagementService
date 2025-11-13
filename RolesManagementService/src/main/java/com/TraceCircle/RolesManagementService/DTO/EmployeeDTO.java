package com.TraceCircle.RolesManagementService.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.TraceCircle.RolesManagementService.Constant.GenderEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

	private Long employeeId;

    private Long systemAdminId;

    private Long organizationId;
    
    private String organizationName;
    
    private String employeeName;
    
    private String employeeEmailId;
    
    private Long employeePhoneNumber;
    
    private LocalDate employeeDOB;
    
    private GenderEnum employeeGender;
    
    private String employeeLocation;
    
    private String employeeDesignation;
    
    private LocalDate employeeJoiningDate;
    
    private Boolean employeeStatus;
    
    private LocalDateTime timeStamp;
}
