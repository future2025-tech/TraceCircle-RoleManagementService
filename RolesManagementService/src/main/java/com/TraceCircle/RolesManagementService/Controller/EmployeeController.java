package com.TraceCircle.RolesManagementService.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TraceCircle.RolesManagementService.DTO.EmployeeDTO;
import com.TraceCircle.RolesManagementService.Service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO dto) {
       
    	log.info("API: Creating employee");
        
    	return ResponseEntity.ok(employeeService.createEmployee(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        
    	return ResponseEntity.ok(employeeService.employeeById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
       
    	return ResponseEntity.ok(employeeService.listOfEmployees());
    }

    @PutMapping("/update")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO dto) {
       
    	return ResponseEntity.ok(employeeService.editEmployee(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        
    	employeeService.deleteEmployee(id);
        
    	return ResponseEntity.ok("Employee deleted successfully");
    }
}
