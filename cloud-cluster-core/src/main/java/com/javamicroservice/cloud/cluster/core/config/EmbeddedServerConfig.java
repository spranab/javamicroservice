package com.javamicroservice.cloud.cluster.core.config;

import java.net.InetAddress;
import java.util.UUID;

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.javamicroservice.cloud.cluster.core.pojo.Instance;
import com.javamicroservice.cloud.cluster.core.pojo.Microservice;
import com.javamicroservice.cloud.cluster.core.util.ApplicationProperties;

@Configuration
public class EmbeddedServerConfig implements
		ApplicationListener<EmbeddedServletContainerInitializedEvent> {

	@Override
	public void onApplicationEvent(
			EmbeddedServletContainerInitializedEvent event) {
		boolean clusteringEnabled = false;
		try {
			clusteringEnabled = Boolean.parseBoolean(ApplicationProperties
					.getPropery("cluster.microsevice.enabled"));
			System.out.println("Clustering enabled?: " + clusteringEnabled);
		} catch (Exception e) {
			e.printStackTrace();
			clusteringEnabled = false;
			System.out.println("Clustering enabled?: " + clusteringEnabled);
		}
		if (clusteringEnabled) {
			int port = event.getEmbeddedServletContainer().getPort();
			RestTemplate restTemplate = new RestTemplate();
			Microservice microservice = new Microservice();
			microservice.setServiceName(ApplicationProperties
					.getPropery("spring.application.name"));
			String registerUrl = ApplicationProperties
					.getPropery("cluster_url") + "register";
			System.out.println("Cluster registration URL: " + registerUrl);
			try {
				microservice.setServiceAddress("http://"
						+ InetAddress.getLocalHost().getHostName());
				Instance instance = new Instance();
				instance.setInstanceId(UUID.randomUUID().toString());
				instance.setPort(port);
				instance.setOnline(true);
				microservice.getInstances().put(instance.getInstanceId(),
						instance);
			} catch (Exception e) {
				e.printStackTrace();
			}
			microservice.setFullPath(ApplicationProperties
					.getPropery(ApplicationProperties.EXECUTOR_FULL_PATH));
			try {
				microservice.setJarFileName(ApplicationProperties
						.getPropery(ApplicationProperties.EXECUTOR_NAME));
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("executing jar: "
					+ microservice.getJarFileName());
			HttpEntity<Microservice> requestEntity = new HttpEntity<Microservice>(
					microservice);
			try {
				ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
						registerUrl, HttpMethod.POST, requestEntity,
						Boolean.class);
				if (responseEntity.getBody()) {
					System.out.println("Registered with cluster "
							+ ApplicationProperties.getPropery("cluster_url"));
				}
			} catch (Exception e) {
				System.out.println("Could not register with cluster "
						+ ApplicationProperties.getPropery("cluster_url"));
				e.printStackTrace();
			}
		}
		System.out.println("***CLOUD_MICROSERVICE_STARTED***");
	}
}
