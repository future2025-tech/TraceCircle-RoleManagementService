package com.TraceCircle.RolesManagementService.DTO;

import java.time.LocalDateTime;

import com.TraceCircle.RolesManagementService.Constant.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

	private Long id;

	private Long systemAdminId;
	
	private String emailId;

	private String passwordHash;  

	private RoleEnum role;

	private LocalDateTime timeStamp;
}
