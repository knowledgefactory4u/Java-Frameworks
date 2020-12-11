package com.knf.spark.api.demo;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.gson.Gson;

public class SparkRestController {
	public static void main(String[] args) {
		final EmployeeService employeeService = new EmployeeServiceImpl();

		post("/employees", (request, response) -> {
			response.type("application/json");

			Employee employee = new Gson().fromJson(request.body(), Employee.class);
			employeeService.addEmployee(employee);

			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
		});

		get("/employees", (request, response) -> {
			response.type("application/json");

			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
					new Gson().toJsonTree(employeeService.getEmployees())));
		});

		get("/employees/:id", (request, response) -> {
			response.type("application/json");

			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
					new Gson().toJsonTree(employeeService.getEmployee(request.params(":id")))));
		});

		put("/employees/:id", (request, response) -> {
			response.type("application/json");

			Employee toEdit = new Gson().fromJson(request.body(), Employee.class);
			Employee editedEmployee = employeeService.editEmployee(toEdit);

			if (editedEmployee != null) {
				return new Gson()
						.toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(editedEmployee)));
			} else {
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
						new Gson().toJson("Employee not found or error in edit")));
			}
		});

		delete("/employees/:id", (request, response) -> {
			response.type("application/json");

			employeeService.deleteEmployee(request.params(":id"));
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "employee deleted successfully"));
		});

	}
}