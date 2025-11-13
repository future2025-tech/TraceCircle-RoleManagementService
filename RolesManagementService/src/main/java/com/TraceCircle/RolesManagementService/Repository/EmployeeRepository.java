package com.TraceCircle.RolesManagementService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TraceCircle.RolesManagementService.Entity.EmployeeEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

	boolean existsByEmployeeNameAndOrganization_OrganizationId(
    		String employeeName, Long organizationId);
}
