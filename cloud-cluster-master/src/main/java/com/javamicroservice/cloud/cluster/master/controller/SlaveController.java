package com.javamicroservice.cloud.cluster.master.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.javamicroservice.cloud.cluster.master.cache.MasterCache;

@Controller
public class SlaveController {
	@Autowired
	private RestTemplate restTemplate;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@RequestMapping(value = "/microservice/start/{slaveId}/{microserviceId}/{instanceId}")
	public String start(@PathVariable String slaveId,
			@PathVariable String microserviceId, @PathVariable String instanceId) {
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity requestEntity = new HttpEntity(headers);
			ResponseEntity<Boolean> response = restTemplate.exchange(
					MasterCache.getSlave(slaveId).getHostAddress()
							+ "/rest/microservice/start/" + microserviceId
							+ "/" + instanceId, HttpMethod.GET, requestEntity,
					Boolean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/microservice/create/{slaveId}/{microserviceId}")
	public String create(@PathVariable String slaveId,
			@PathVariable String microserviceId) {
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity requestEntity = new HttpEntity(headers);
			ResponseEntity<Boolean> response = restTemplate.exchange(
					MasterCache.getSlave(slaveId).getHostAddress()
							+ "/rest/microservice/create/" + microserviceId,
					HttpMethod.GET, requestEntity, Boolean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/microservice/stop/{slaveId}/{microserviceId}/{instanceId}")
	public String stop(@PathVariable String slaveId,
			@PathVariable String microserviceId, @PathVariable String instanceId) {
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity requestEntity = new HttpEntity(headers);
			ResponseEntity<Boolean> response = restTemplate.exchange(
					MasterCache.getSlave(slaveId).getHostAddress()
							+ "/rest/microservice/stop/" + microserviceId + "/"
							+ instanceId, HttpMethod.GET, requestEntity,
					Boolean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
}
