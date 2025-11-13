package com.TraceCircle.RolesManagementService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TraceCircle.RolesManagementService.Entity.DepartmentEntity;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    boolean existsByDepartmentNameAndOrganization_OrganizationId(
    		String departmentName, Long organizationId);

}
