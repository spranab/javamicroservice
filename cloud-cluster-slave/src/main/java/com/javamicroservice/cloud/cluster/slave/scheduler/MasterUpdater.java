package com.javamicroservice.cloud.cluster.slave.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.javamicroservice.cloud.cluster.core.pojo.Slave;
import com.javamicroservice.cloud.cluster.core.util.ApplicationProperties;
import com.javamicroservice.cloud.cluster.slave.repo.SlaveRepository;

@Component
public class MasterUpdater {
	@Autowired
	private RestTemplate restTemplate;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Scheduled(fixedRateString = "${cluster.microsevice.check.rate}")
	public void updateMaster() {
		System.out.println("Executing master update on Master: "
				+ ApplicationProperties.getPropery("cluster.master.address"));
		HttpEntity<Slave> requestEntity = new HttpEntity<Slave>(
				SlaveRepository.getSlave());
		ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
				ApplicationProperties.getPropery("cluster.master.address")
						+ "/register", HttpMethod.POST, requestEntity,
				Boolean.class);
	}
}
