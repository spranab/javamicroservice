package com.javamicroservice.cloud.cluster.slave.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javamicroservice.cloud.cluster.core.pojo.Slave;
import com.javamicroservice.cloud.cluster.slave.repo.SlaveRepository;

@RestController
public class MasterUpdate {
	@RequestMapping(value = "/getUpdate", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Slave> getUpdate() {
		ResponseEntity<Slave> responseEntity = new ResponseEntity<Slave>(
				SlaveRepository.getSlave(), HttpStatus.OK);
		return responseEntity;
	}
}
