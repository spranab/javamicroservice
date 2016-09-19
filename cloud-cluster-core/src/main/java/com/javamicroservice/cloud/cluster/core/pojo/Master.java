package com.javamicroservice.cloud.cluster.core.pojo;

import java.util.HashMap;
import java.util.Map;

public class Master {
	private int masterId;
	private String masterName;
	private Map<String, Slave> slaveMap = new HashMap<String, Slave>();

	public int getMasterId() {
		return masterId;
	}

	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public Map<String, Slave> getSlaveMap() {
		return slaveMap;
	}

	public void setSlaveMap(Map<String, Slave> slaveMap) {
		this.slaveMap = slaveMap;
	}

}
