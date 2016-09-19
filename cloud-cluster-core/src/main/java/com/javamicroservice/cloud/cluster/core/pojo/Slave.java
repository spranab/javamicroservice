package com.javamicroservice.cloud.cluster.core.pojo;

import java.util.HashMap;
import java.util.Map;

public class Slave {
	private String slaveId;
	private String clusterName;
	private String hostAddress;
	private boolean online;
	private Map<String, Microservice> microserviceMap = new HashMap<String, Microservice>();

	public String getSlaveId() {
		return slaveId;
	}

	public void setSlaveId(String slaveId) {
		this.slaveId = slaveId;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
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
