package com.TraceCircle.RolesManagementService.DTO;

import org.hibernate.validator.constraints.NotBlank;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class ForgotPasswordRequestDTO {
   
	@NotBlank 
	@Email
    private String emailId;
}
