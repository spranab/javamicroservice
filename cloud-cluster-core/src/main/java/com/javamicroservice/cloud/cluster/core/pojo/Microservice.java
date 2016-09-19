package com.javamicroservice.cloud.cluster.core.pojo;

import java.util.HashMap;

public class Microservice {
	private String serviceId;
	private String serviceName;
	private String serviceAddress;
	private String fullPath;
	private String jarFileName;
	private HashMap<String, Instance> instances = new HashMap<String, Instance>();

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getJarFileName() {
		return jarFileName;
	}

	public void setJarFileName(String jarFileName) {
		this.jarFileName = jarFileName;
	}

	public HashMap<String, Instance> getInstances() {
		return instances;
	}

	public void setInstances(HashMap<String, Instance> instances) {
		this.instances = instances;
	}

}
