package com.TraceCircle.RolesManagementService.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class OtpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailId;
    
    private String otp;
    
    private LocalDateTime expiresAt;
    
    private boolean used;
}

