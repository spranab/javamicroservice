package com.javamicroservice.cloud.cluster.slave.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javamicroservice.cloud.cluster.core.pojo.Microservice;
import com.javamicroservice.cloud.cluster.slave.repo.SlaveRepository;

@RestController
public class Registrar {

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> register(
			@RequestBody Microservice microservice) {
		ResponseEntity<Boolean> responseEntity;
		System.out.println("Registered new service: "
				+ microservice.getServiceName());
		System.out.println("Full path: " + microservice.getFullPath());
		System.out.println("Jar file: " + microservice.getJarFileName());
		SlaveRepository.addMicroservice(microservice);
		responseEntity = new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return responseEntity;
	}
}
