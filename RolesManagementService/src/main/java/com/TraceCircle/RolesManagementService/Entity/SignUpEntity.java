package com.TraceCircle.RolesManagementService.Entity;

import com.TraceCircle.RolesManagementService.Constant.RoleEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TC_SIGNUP")
public class SignUpEntity {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String emailId;
	
	private String password;
	
	private String confirmPassword;
	
	private RoleEnum role;
}
