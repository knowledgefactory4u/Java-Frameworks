package com.knf.demo.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.knf.demo.controller.EmployeeController;

@Component
@ApplicationPath("/boot-jersey-knf")
public class JerseyConfiguration extends ResourceConfig {
	public JerseyConfiguration() {
		register(EmployeeController.class);
	}
}