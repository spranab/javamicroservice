package com.javamicroservice.cloud.cluster.master.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javamicroservice.cloud.cluster.core.pojo.Slave;
import com.javamicroservice.cloud.cluster.master.cache.MasterCache;

@RestController
public class Registrar {

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> register(@RequestBody Slave slave) {
		ResponseEntity<Boolean> responseEntity;
		System.out.println("Registering/Updating cluster: "
				+ slave.getClusterName());
		MasterCache.addSlave(slave);
		responseEntity = new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return responseEntity;
	}
}
