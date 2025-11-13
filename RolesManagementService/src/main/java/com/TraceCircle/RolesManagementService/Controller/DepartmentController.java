package com.TraceCircle.RolesManagementService.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TraceCircle.RolesManagementService.DTO.DepartmentDTO;
import com.TraceCircle.RolesManagementService.ServiceIMPL.DepartmentServiceIMPL;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentServiceIMPL departmentService;

    @PostMapping("/create")
    public ResponseEntity<DepartmentDTO> create(@RequestBody DepartmentDTO dto) {
       
    	log.info("API: Creating department: {}", dto.getDepartmentName());
        
    	DepartmentDTO response = departmentService.createDepartment(dto);
        
    	return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getById(@PathVariable Long id) {
       
    	log.info("API: Fetching department id={}", id);
        
    	return ResponseEntity.ok(departmentService.departmentById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDTO>> getAll() {
       
    	log.info("API: Fetching all departments");
        
    	return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PutMapping("/update")
    public ResponseEntity<DepartmentDTO> update(@RequestBody DepartmentDTO dto) {
       
    	log.info("API: Updating department id={}", dto.getDepartmentId());
       
    	return ResponseEntity.ok(departmentService.editDepartment(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
      
    	log.warn("API: Deleting department id={}", id);
        
    	departmentService.delete(id);
        
    	return ResponseEntity.ok("Department deleted successfully");
    }
}
