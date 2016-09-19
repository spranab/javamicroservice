package com.javamicroservice.cloud.cluster.core.pojo;

import java.util.HashMap;
import java.util.Map;

public class Slave {
	private int slaveId;
	private String slaveName;
	private boolean online;
	private Map<String, Microservice> microserviceMap = new HashMap<String, Microservice>();

	public int getSlaveId() {
		return slaveId;
	}

	public void setSlaveId(int slaveId) {
		this.slaveId = slaveId;
	}

	public String getSlaveName() {
		return slaveName;
	}

	public void setSlaveName(String slaveName) {
		this.slaveName = slaveName;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Map<String, Microservice> getMicroserviceMap() {
		return microserviceMap;
	}

	public void setMicroserviceMap(Map<String, Microservice> microserviceMap) {
		this.microserviceMap = microserviceMap;
	}

}
