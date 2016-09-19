package com.javamicroservice.cloud.cluster.master.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.javamicroservice.cloud.cluster.core.pojo.Slave;
import com.javamicroservice.cloud.cluster.master.cache.MasterCache;

@Component
public class UpdateSlaveStatus {
	@Autowired
	private RestTemplate restTemplate;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Scheduled(fixedRateString = "${cluster.slave.check.rate}")
	public void updateStatus() {
		System.out.println("Executing Slave Health Check Job");
		for (String key : MasterCache.getMaster().getSlaveMap().keySet()) {
			Slave slave = MasterCache.getMaster().getSlaveMap().get(key);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity requestEntity = new HttpEntity(headers);
			try {
				ResponseEntity<Slave> responseEntity = restTemplate.exchange(
						slave.getHostAddress() + "/getUpdate", HttpMethod.GET,
						requestEntity, Slave.class);
				if (responseEntity.getBody() != null) {
					MasterCache.addSlave(responseEntity.getBody());
				} else {
					MasterCache.getMaster().getSlaveMap().get(key)
							.setOnline(false);
				}
			} catch (Exception e) {
				MasterCache.getMaster().getSlaveMap().get(key).setOnline(false);
			}
		}

	}
}
