package com.TraceCircle.RolesManagementService.Entity;

import java.time.LocalDateTime;

import com.TraceCircle.RolesManagementService.Constant.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Entity 
@AllArgsConstructor 
@NoArgsConstructor
@Table(name = "TC_SYSTEMADMIN_SIGNUP")
public class SignUpEntity {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "system_admin_id", referencedColumnName = "id", nullable = false)
    private SystemAdminOnboardingEntity systemAdmin;

    @Column(unique = true, nullable = false, length = 180)
    private String emailId;

    @Column(nullable = false, length = 80)
    private String passwordHash;  

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RoleEnum role;
	
	@Column(name = "reset_otp")
    private String resetOtp;

	@Column(name = "otp_expiry")
    private LocalDateTime otpExpiry;

    private LocalDateTime timeStamp;
    
    @PrePersist
    void onCreated(){ 
	
    	this.timeStamp = LocalDateTime.now().withNano(0);   	
    }
}
