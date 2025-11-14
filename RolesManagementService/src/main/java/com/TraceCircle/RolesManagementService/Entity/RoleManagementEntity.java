package com.TraceCircle.RolesManagementService.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.TraceCircle.RolesManagementService.Constant.EmployeeLevelEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TC_ROLE_MANAGEMENT")
public class RoleManagementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleManagementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_admin_id", nullable = false)
    private SystemAdminOnboardingEntity systemAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private OrganizationEntity organization;

    private String roleName;

    @Enumerated(EnumType.STRING)
    private EmployeeLevelEnum empLevel;

    private String employeeDescription;

    private Boolean isActive;

    private LocalDate createdOn;

    private LocalDateTime timeStamp;

    @PrePersist
    void createdAt() {
        this.createdOn = LocalDate.now();
        this.timeStamp = LocalDateTime.now().withNano(0);
    }
}
