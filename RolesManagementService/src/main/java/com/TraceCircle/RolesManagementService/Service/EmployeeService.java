package com.TraceCircle.RolesManagementService.Service;

import java.util.List;

import com.TraceCircle.RolesManagementService.DTO.EmployeeDTO;

public interface EmployeeService {

	EmployeeDTO createEmployee(EmployeeDTO empDTO);
	
	EmployeeDTO employeeById(Long id);
	
	List<EmployeeDTO> listOfEmployees();
	
	EmployeeDTO editEmployee(EmployeeDTO empDTO);
	
	void deleteEmployee(Long id);
}
