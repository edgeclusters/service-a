package com.edgeclusters.academy.servicea;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;

@RestController
class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	private final EmployeeRepository repository;

	EmployeeController(EmployeeRepository repository) {
		this.repository = repository;
	}

	@Timed(value = "employees.all.time", description = "Time taken to get all employees")
	@GetMapping("/employees")
	List<Employee> all() {
		logger.info("GET all employees");
		return repository.findAll();
	}
	// end::get-aggregate-root[]

	@Timed(value = "employees.create.time", description = "Time taken to create new employee")
	@PostMapping("/employees")
	Employee newEmployee(@RequestBody Employee newEmployee) {
		logger.info("POST employee: " + newEmployee);
		return repository.save(newEmployee);
	}

	@Timed(value = "employee.get.time", description = "Time taken to get a single employee")
	@GetMapping("/employees/{id}")
	Employee one(@PathVariable Long id) {
		logger.info("GET employee: " + id);
		return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	@Timed(value = "employee.put.time", description = "Time taken to update a single employee")
	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

		logger.info("PUT employee id: " + id);
		return repository.findById(id).map(employee -> {
			employee.setName(newEmployee.getName());
			employee.setRole(newEmployee.getRole());
			return repository.save(employee);
		}).orElseGet(() -> {
			newEmployee.setId(id);
			return repository.save(newEmployee);
		});
	}

	@Timed(value = "employee.delete.time", description = "Time taken to get a single employee by ID")
	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		logger.info("DELETE employe id: " + id);
		repository.deleteById(id);
	}
}