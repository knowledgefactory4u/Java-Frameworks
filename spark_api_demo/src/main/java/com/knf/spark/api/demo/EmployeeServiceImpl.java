package com.knf.spark.api.demo;

import java.util.Collection;
import java.util.HashMap;

public class EmployeeServiceImpl implements EmployeeService {
	private HashMap<String, Employee> employeeMap;

	public EmployeeServiceImpl() {
		employeeMap = new HashMap<>();
	}

	@Override
	public void addEmployee(Employee emp) {
		employeeMap.put(emp.getId(), emp);
	}

	@Override
	public Collection<Employee> getEmployees() {
		return employeeMap.values();
	}

	@Override
	public Employee getEmployee(String id) {
		return employeeMap.get(id);
	}

	@Override
	public Employee editEmployee(Employee forEdit) throws EmployeeException {
		try {
			if (forEdit.getId() == null)
				throw new EmployeeException("ID cannot be blank");

			Employee toEdit = employeeMap.get(forEdit.getId());

			if (toEdit == null)
				throw new EmployeeException("Employee not found");

			if (forEdit.getEmail() != null) {
				toEdit.setEmail(forEdit.getEmail());
			}
			if (forEdit.getFirstName() != null) {
				toEdit.setFirstName(forEdit.getFirstName());
			}
			if (forEdit.getLastName() != null) {
				toEdit.setLastName(forEdit.getLastName());
			}
			if (forEdit.getId() != null) {
				toEdit.setId(forEdit.getId());
			}

			return toEdit;
		} catch (Exception ex) {
			throw new EmployeeException(ex.getMessage());
		}
	}

	@Override
	public void deleteEmployee(String id) {
		employeeMap.remove(id);
	}

	@Override
	public boolean employeeExist(String id) {
		return employeeMap.containsKey(id);
	}

}