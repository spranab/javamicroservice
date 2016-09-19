package com.javamicroservice.cloud.cluster.slave.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.javamicroservice.cloud.cluster.core.pojo.Instance;
import com.javamicroservice.cloud.cluster.core.pojo.Microservice;
import com.javamicroservice.cloud.cluster.slave.repo.SlaveRepository;

@Component
public class CheckMicroserviceStatus {
	@Autowired
	private RestTemplate restTemplate;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Scheduled(fixedRateString = "${cluster.microsevice.check.rate}")
	public void updateMicroserviceStatus() {
		System.out.println("Executing Microservice Health Check Job");
		for (String key : SlaveRepository.getSlave().getMicroserviceMap()
				.keySet()) {
			Microservice microservice = SlaveRepository.getSlave()
					.getMicroserviceMap().get(key);
			for (String instanceKey : microservice.getInstances().keySet()) {
				Instance instance = microservice.getInstances()
						.get(instanceKey);
				HttpHeaders headers = new HttpHeaders();
				HttpEntity requestEntity = new HttpEntity(headers);
				try {
					ResponseEntity<Boolean> responseEntity = restTemplate
							.exchange(microservice.getServiceAddress() + ":"
									+ instance.getPort() + "/checkifup",
									HttpMethod.GET, requestEntity,
									Boolean.class);
					if (responseEntity.getBody()) {
						SlaveRepository.getSlave().getMicroserviceMap()
								.get(key).getInstances().get(instanceKey)
								.setOnline(true);
					} else {
						SlaveRepository.getSlave().getMicroserviceMap()
								.get(key).getInstances().get(instanceKey)
								.setOnline(false);
					}
				} catch (Exception e) {
					SlaveRepository.getSlave().getMicroserviceMap().get(key)
							.getInstances().get(instanceKey).setOnline(false);
				}
			}

		}
	}
}
