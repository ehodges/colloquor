package com.bale_twine.colloquor;

import com.bale_twine.colloquor.resources.HelloWorldResource;
import com.bale_twine.colloquor.health.TemplateHealthCheck;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class HelloWorldService extends Service<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldService().run(args);
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.setName("colloquor");
    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment) {
		final String template = configuration.getTemplate();
		final String defaultName = configuration.getDefaultName();
		environment.addResource(new HelloWorldResource(template, defaultName));
		environment.addHealthCheck(new TemplateHealthCheck(template));
    }

}
