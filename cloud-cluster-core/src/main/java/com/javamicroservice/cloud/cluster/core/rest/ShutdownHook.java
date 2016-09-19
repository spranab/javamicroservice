package com.javamicroservice.cloud.cluster.core.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javamicroservice.cloud.cluster.core.util.ApplicationProperties;

@RestController
public class ShutdownHook {
	@Autowired
	private ApplicationContext applicationContext;

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@RequestMapping(value = "/shutdown")
	public void shutdown() {
		if ("true".equalsIgnoreCase(ApplicationProperties
				.getPropery("remote.shutdown.enabled"))) {
			SpringApplication.exit(applicationContext, new ExitCodeGenerator() {

				@Override
				public int getExitCode() {
					return 0;
				}
			});
		}
	}
}
