package com.TraceCircle.RolesManagementService.DTO;

import com.TraceCircle.RolesManagementService.Constant.RoleEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class SignupRequest {

    @NotNull(message = "systemAdminId is required")
    private Long systemAdminId;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String emailId;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Confirm password cannot be empty")
    private String confirmPassword;

    @NotNull(message = "Role is required")
    private RoleEnum role;
}
