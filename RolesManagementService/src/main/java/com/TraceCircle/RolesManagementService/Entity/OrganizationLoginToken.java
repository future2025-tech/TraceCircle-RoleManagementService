package com.TraceCircle.RolesManagementService.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Entity
@Table(name = "TC_ORG_LOGIN_TOKEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationLoginToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_user_id")
    private OrganizationUserEntity orgUser;

    @Column(nullable = false, unique = true)
    private String token;

    private LocalDateTime expiry;

    @PrePersist
    void created() { 
    	expiry = LocalDateTime.now().plusHours(24).withNano(0); 	
    }
}
