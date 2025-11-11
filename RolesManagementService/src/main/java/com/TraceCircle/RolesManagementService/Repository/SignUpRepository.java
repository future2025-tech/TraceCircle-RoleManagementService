package com.TraceCircle.RolesManagementService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TraceCircle.RolesManagementService.Entity.SignUpEntity;

@Repository
public interface SignUpRepository extends JpaRepository<SignUpEntity, Long> {

	Optional<SignUpEntity> findByEmailId(String email);
    
	boolean existsByEmailId(String email);
}
