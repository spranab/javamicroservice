package com.javamicroservice.cloud.cluster.slave.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.javamicroservice.cloud.cluster.core.util.ApplicationProperties;
import com.javamicroservice.cloud.cluster.slave.repo.SlaveRepository;

@Controller
public class MicroserviceController {
	@Autowired
	private RestTemplate restTemplate;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@RequestMapping(value = "/microservice/start/{microserviceId}/{instanceId}")
	public String start(@PathVariable String microserviceId,
			@PathVariable String instanceId) {
		// ResponseEntity<Boolean> responseEntity;
		String javaCommand;
		if (ApplicationProperties.getPropery("java_home") != null) {
			javaCommand = "\"" + ApplicationProperties.getPropery("java_home")
					+ "/bin/java\"";
		} else {
			javaCommand = "java";
		}
		try {

			ProcessBuilder pb = new ProcessBuilder(javaCommand, "-jar",
					SlaveRepository.getMicroservice(microserviceId)
							.getFullPath(), "--server.port="
							+ SlaveRepository.getMicroservice(microserviceId)
									.getInstances().get(instanceId).getPort());
			Process p = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String s = "";
			while ((s = in.readLine()) != null) {
				if (s.contains("***CLOUD_MICROSERVICE_STARTED***")) {
					// responseEntity = new ResponseEntity<Boolean>(true,
					// HttpStatus.OK);
					return "redirect:/";
				}
				System.out.println(s);
			}
			int status = p.waitFor();
			System.out.println("Exited with status: " + status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// responseEntity = new ResponseEntity<Boolean>(false, HttpStatus.OK);
		return "redirect:/";
	}

	@RequestMapping(value = "/microservice/create/{microserviceId}")
	public String create(@PathVariable String microserviceId) {
		// ResponseEntity<Boolean> responseEntity;
		String javaCommand;
		if (ApplicationProperties.getPropery("java_home") != null) {
			javaCommand = "\"" + ApplicationProperties.getPropery("java_home")
					+ "/bin/java\"";
		} else {
			javaCommand = "java";
		}
		try {

			ProcessBuilder pb = new ProcessBuilder(javaCommand, "-jar",
					SlaveRepository.getMicroservice(microserviceId)
							.getFullPath());
			Process p = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String s = "";
			while ((s = in.readLine()) != null) {
				if (s.contains("***CLOUD_MICROSERVICE_STARTED***")) {
					// responseEntity = new ResponseEntity<Boolean>(true,
					// HttpStatus.OK);
					return "redirect:/";
				}
				System.out.println(s);
			}
			int status = p.waitFor();
			System.out.println("Exited with status: " + status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// responseEntity = new ResponseEntity<Boolean>(false, HttpStatus.OK);
		return "redirect:/";
	}

	@RequestMapping(value = "/microservice/stop/{microserviceId}/{instanceId}")
	public String stop(@PathVariable String microserviceId,
			@PathVariable String instanceId) {
		ResponseEntity<Boolean> responseEntity;
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity requestEntity = new HttpEntity(headers);
			ResponseEntity<Boolean> response = restTemplate.exchange(
					SlaveRepository.getMicroservice(microserviceId)
							.getServiceAddress()
							+ ":"
							+ SlaveRepository.getMicroservice(microserviceId)
									.getInstances().get(instanceId).getPort()
							+ "/shutdown", HttpMethod.GET, requestEntity,
					Boolean.class);

		} catch (Exception e) {
			try {
				HttpHeaders headers = new HttpHeaders();
				HttpEntity requestEntity = new HttpEntity(headers);
				ResponseEntity<Boolean> checkResponse = restTemplate.exchange(
						SlaveRepository.getMicroservice(microserviceId)
								.getServiceAddress()
								+ ":"
								+ SlaveRepository
										.getMicroservice(microserviceId)
										.getInstances().get(instanceId)
										.getPort() + "/checkifup",
						HttpMethod.GET, requestEntity, Boolean.class);
			} catch (Exception e2) {
				SlaveRepository.getSlave().getMicroserviceMap()
						.get(microserviceId).getInstances().get(instanceId)
						.setOnline(false);
				// responseEntity = new ResponseEntity<Boolean>(true,
				// HttpStatus.OK);
				return "redirect:/";
			}

		}
		// responseEntity = new ResponseEntity<Boolean>(false, HttpStatus.OK);
		// return responseEntity;
		return "redirect:/";
	}
}
