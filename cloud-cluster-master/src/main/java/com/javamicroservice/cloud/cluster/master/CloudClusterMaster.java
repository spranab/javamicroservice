package com.javamicroservice.cloud.cluster.master;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.javamicroservice.cloud.cluster.core.CloudClusterCore;
import com.javamicroservice.cloud.cluster.master.cache.ClusterInfoCache;

@SpringBootApplication
@ComponentScan(basePackageClasses = { CloudClusterMaster.class,
		CloudClusterCore.class })
@EnableScheduling
public class CloudClusterMaster implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(CloudClusterMaster.class, args);
	}

	public void run(String... arg0) throws Exception {
		ClusterInfoCache.getStartTimestamp();
	}

}
