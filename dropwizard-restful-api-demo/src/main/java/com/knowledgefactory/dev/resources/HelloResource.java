package com.knowledgefactory.dev.resources;

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.knowledgefactory.dev.api.Message;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloResource {
	private final String defaultName;

	public HelloResource(final String defaultName) {
		this.defaultName = defaultName;
	}

	@GET
	public Message get(@QueryParam("name") Optional<String> name) {
		Message hello = new Message();
		hello.setMessage(("Hello " + name.orElse(defaultName) + " welcome to dropwizard"));
		return hello;
	}
}
