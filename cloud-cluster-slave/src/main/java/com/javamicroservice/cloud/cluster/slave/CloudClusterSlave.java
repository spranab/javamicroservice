package com.javamicroservice.cloud.cluster.slave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.javamicroservice.cloud.cluster.core.CloudClusterCore;
import com.javamicroservice.cloud.cluster.slave.cache.ClusterInfoCache;

@SpringBootApplication
@ComponentScan(basePackageClasses = { CloudClusterSlave.class,
		CloudClusterCore.class })
@EnableScheduling
public class CloudClusterSlave implements CommandLineRunner {

	@Autowired
	private RestTemplate restTemplate;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(CloudClusterSlave.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		ClusterInfoCache.getStartTimestamp();
	}
}
