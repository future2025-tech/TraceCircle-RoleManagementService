package com.TraceCircle.RolesManagementService.Entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TC_DEPARTMENT")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_admin_id", nullable = false)
    private SystemAdminOnboardingEntity systemAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private OrganizationEntity organization;

    @Column(nullable = false)
    private String departmentName;

    private String description;

    private Boolean isActive = true;

    private LocalDateTime timeStamp;

    @PrePersist
    void onCreated() {
        this.timeStamp = LocalDateTime.now().withNano(0);
    }
}
