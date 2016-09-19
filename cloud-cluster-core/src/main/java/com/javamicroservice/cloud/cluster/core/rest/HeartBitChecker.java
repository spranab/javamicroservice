package com.javamicroservice.cloud.cluster.core.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartBitChecker {
	@RequestMapping(value = "checkifup")
	public ResponseEntity<Boolean> checkIfUp() {
		ResponseEntity<Boolean> responseEntity = new ResponseEntity<Boolean>(
				true, HttpStatus.OK);
		return responseEntity;
	}
}
