package com.TraceCircle.RolesManagementService.DTO;

import java.time.LocalDate;

import com.TraceCircle.RolesManagementService.Constant.EmployeeLevelEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleManagementDTO {

    private Long roleManagementId;

    private Long organizationId;

    private String roleName;

    private EmployeeLevelEnum empLevel;

    private String employeeDescription;

    private Boolean isActive;
    
    private LocalDate createdOn;
    
}

