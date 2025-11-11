package com.TraceCircle.RolesManagementService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TraceCircle.RolesManagementService.Entity.OtpEntity;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    
	Optional<OtpEntity> findTopByEmailIdAndUsedFalseOrderByIdDesc(String emailId);
}
