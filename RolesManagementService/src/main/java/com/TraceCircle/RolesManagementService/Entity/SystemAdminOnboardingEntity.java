package com.TraceCircle.RolesManagementService.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TC_SYSTEMADMIN_ONBOARDING")
public class SystemAdminOnboardingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String systemName;
	
	private String systemAddress;
	
	private String systemRegion;
	
	private String systemCity;
	
	private Long systemPostalcode;
	
	private String systemEmailId;
	
	private Long systemPhoneNumber;
	
	private LocalDateTime createdAt;
	
	@PrePersist
	void onCreated() {
		this.createdAt = LocalDateTime.now().minusNanos(0);
	}
	
}
