package com.javamicroservice.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.javamicroservice.cloud.cluster.core.CloudClusterCore;

@SpringBootApplication
@ComponentScan(basePackageClasses = { App.class, CloudClusterCore.class })
public class App implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	public void run(String... arg0) throws Exception {
		System.out.println("running");
	}
}
