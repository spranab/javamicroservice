package com.javamicroservice.cloud.cluster.slave.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.javamicroservice.cloud.cluster.core.util.ApplicationProperties;
import com.javamicroservice.cloud.cluster.slave.repo.SlaveRepository;
import com.javamicroservice.cloud.cluster.slave.scheduler.MasterUpdater;

@RestController
public class MicroserviceRestController {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MasterUpdater masterUpdater;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public MasterUpdater getMasterUpdater() {
		return masterUpdater;
	}

	public void setMasterUpdater(MasterUpdater masterUpdater) {
		this.masterUpdater = masterUpdater;
	}

	@RequestMapping(value = "/rest/microservice/start/{microserviceId}/{instanceId}")
	public ResponseEntity<Boolean> start(@PathVariable String microserviceId,
			@PathVariable String instanceId) {
		ResponseEntity<Boolean> responseEntity;
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
					responseEntity = new ResponseEntity<Boolean>(true,
							HttpStatus.OK);
					SlaveRepository.getMicroservice(microserviceId)
							.getInstances().get(instanceId).setOnline(true);
					masterUpdater.updateMaster();
					return responseEntity;
				}
				System.out.println(s);
			}
			int status = p.waitFor();
			System.out.println("Exited with status: " + status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseEntity = new ResponseEntity<Boolean>(false, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(value = "/rest/microservice/create/{microserviceId}")
	public ResponseEntity<Boolean> create(@PathVariable String microserviceId) {
		ResponseEntity<Boolean> responseEntity;
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
					responseEntity = new ResponseEntity<Boolean>(true,
							HttpStatus.OK);
					masterUpdater.updateMaster();
					return responseEntity;
				}
				System.out.println(s);
			}
			int status = p.waitFor();
			System.out.println("Exited with status: " + status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseEntity = new ResponseEntity<Boolean>(false, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(value = "/rest/microservice/stop/{microserviceId}/{instanceId}")
	public ResponseEntity<Boolean> stop(@PathVariable String microserviceId,
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
				responseEntity = new ResponseEntity<Boolean>(true,
						HttpStatus.OK);
				SlaveRepository.getMicroservice(microserviceId).getInstances()
						.get(instanceId).setOnline(false);
				masterUpdater.updateMaster();
				return responseEntity;
			}

		}
		responseEntity = new ResponseEntity<Boolean>(false, HttpStatus.OK);
		return responseEntity;
	}
}
