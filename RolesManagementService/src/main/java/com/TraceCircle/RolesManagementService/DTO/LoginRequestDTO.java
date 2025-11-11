package com.TraceCircle.RolesManagementService.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class LoginRequestDTO {
    
    @NotBlank
    @Email
    private String emailId;

    @NotBlank
    private String password;
}
