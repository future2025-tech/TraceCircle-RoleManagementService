package com.TraceCircle.RolesManagementService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TraceCircle.RolesManagementService.Entity.OrganizationLoginToken;

@Repository
public interface OrganizationLoginTokenRepository extends JpaRepository<OrganizationLoginToken, Long> {
    
	Optional<OrganizationLoginToken> findByToken(String token);
}
