package com.knf.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.knf.demo.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}