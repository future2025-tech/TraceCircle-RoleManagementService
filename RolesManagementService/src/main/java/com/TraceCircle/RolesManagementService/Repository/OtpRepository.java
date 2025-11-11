package com.TraceCircle.RolesManagementService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TraceCircle.RolesManagementService.Entity.OtpEntity;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    
	Optional<OtpEntity> findTopByEmailIdAndUsedFalseOrderByIdDesc(String emailId);
}
