package com.sample.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.springboot.entity.Employee;
import com.sample.springboot.exception.ResourceNotFoundException;
import com.sample.springboot.repository.EmployeeRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/")

public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	// get all employee
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	//get single employee by id
	@GetMapping("/employees/{empId}")
	public ResponseEntity<Employee> getEmpById(@PathVariable long empId) {
		Employee employee= employeeRepository.findById(empId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee with "+empId+"not present"));
		return ResponseEntity.ok(employee);
	}
	
	//create employee
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//delete employee
	@DeleteMapping("/employees/{empId}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable long empId){
		Employee employee= employeeRepository.findById(empId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee with "+empId+"not present"));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
	//update employee
	@PutMapping("/employees/{empId}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long empId,@RequestBody Employee employeeDetails) {
		Employee employee= employeeRepository.findById(empId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee with "+empId+"not present"));
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updateEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updateEmployee); 
	}
}
