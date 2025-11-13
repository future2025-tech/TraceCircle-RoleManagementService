package com.TraceCircle.RolesManagementService.Service;

import java.util.List;

import com.TraceCircle.RolesManagementService.DTO.DepartmentDTO;

public interface DepartmentService {

	DepartmentDTO createDepartment(DepartmentDTO deptDTO);
	
	DepartmentDTO departmentById(Long id);
	
	List<DepartmentDTO> getAllDepartments();
	
	DepartmentDTO editDepartment(DepartmentDTO deptDTO);
	
	void delete(Long id);
	
}
