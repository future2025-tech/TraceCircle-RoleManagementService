package com.TraceCircle.RolesManagementService.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "TC_ORGANIZATION_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private OrganizationEntity organization;

    @Column(nullable = false, unique = true)
    private String email;  

    @Column(nullable = true) 
    private String passwordHash;

    private Boolean active = false; // becomes true after password setup

    private LocalDateTime createdAt;

    @PrePersist
    void created() { 
    	
    	createdAt = LocalDateTime.now().withNano(0); 	
    }
}
