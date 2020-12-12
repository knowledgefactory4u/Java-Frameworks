package com.knowledgefactory.dev;

import com.knowledgefactory.dev.resources.HelloResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class DemoApplication extends Application<DemoApplicationConfiguration> {

	public static void main(String[] args) throws Exception {
		new DemoApplication().run(args);
	}

	@Override
	public void run(DemoApplicationConfiguration configuration, Environment environment) {
		environment.jersey().register(new HelloResource(configuration.getDefaultName()));
	}
}
