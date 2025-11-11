package com.TraceCircle.RolesManagementService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TraceCircle.RolesManagementService.Entity.OrganizationUserEntity;

@Repository
public interface OrganizationUserRepository extends JpaRepository<OrganizationUserEntity, Long> {
   
	Optional<OrganizationUserEntity> findByEmail(String email);
    
	boolean existsByEmail(String email);
}
