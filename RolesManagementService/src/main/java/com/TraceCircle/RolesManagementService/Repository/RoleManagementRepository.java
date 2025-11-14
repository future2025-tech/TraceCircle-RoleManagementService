package com.TraceCircle.RolesManagementService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TraceCircle.RolesManagementService.Entity.RoleManagementEntity;

@Repository
public interface RoleManagementRepository extends JpaRepository<RoleManagementEntity, Long> {

	List<RoleManagementEntity> findByOrganization_OrganizationId(Long organizationId);

    boolean existsByRoleNameAndOrganization_OrganizationId(String roleName, Long orgId);

}
