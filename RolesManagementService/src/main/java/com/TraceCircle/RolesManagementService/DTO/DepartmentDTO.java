package com.TraceCircle.RolesManagementService.DTO;

import java.time.LocalDateTime;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
   
	private Long departmentId;
    
	private Long systemAdminId;
    
	private Long organizationId;
    
	private String organizationName;
    
	private String departmentName;
    
	private String description;
    
	private Boolean isActive;
    
	private LocalDateTime timeStamp;
}
