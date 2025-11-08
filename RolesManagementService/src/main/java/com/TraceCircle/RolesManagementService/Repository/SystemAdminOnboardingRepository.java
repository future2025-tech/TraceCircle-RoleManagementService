package com.TraceCircle.RolesManagementService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TraceCircle.RolesManagementService.Entity.SystemAdminOnboardingEntity;

@Repository
public interface SystemAdminOnboardingRepository extends JpaRepository<SystemAdminOnboardingEntity, Long> {

}
