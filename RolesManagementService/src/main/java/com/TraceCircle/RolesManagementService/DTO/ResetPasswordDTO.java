package com.TraceCircle.RolesManagementService.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class ResetPasswordDTO {
    
	@NotBlank 
	@Email
    private String emailId;

    @NotBlank
    private String otp;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;
}
