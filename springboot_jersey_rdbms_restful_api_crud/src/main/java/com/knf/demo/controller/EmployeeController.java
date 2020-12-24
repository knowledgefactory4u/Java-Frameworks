package com.knf.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.knf.demo.model.Employee;
import com.knf.demo.repository.EmployeeRepository;

@Component
@Path("/api/v1")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GET
	@Produces("application/json")
	@Path("/employees")
	public List<Employee> getAllEmployees() {
		return (List<Employee>) employeeRepository.findAll();
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathParam(value = "id") Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow();
		return ResponseEntity.ok().body(employee);
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/employees")
	@PostMapping("/employees")
	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathParam(value = "id") Long employeeId,
			@RequestBody Employee employeeDetails) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow();

		employee.setEmailId(employeeDetails.getEmailId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		return ResponseEntity.ok(employeeRepository.save(employee));
	}

	@DELETE
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathParam(value = "id") Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow();

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}