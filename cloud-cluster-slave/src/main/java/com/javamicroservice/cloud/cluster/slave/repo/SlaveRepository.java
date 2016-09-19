package com.javamicroservice.cloud.cluster.slave.repo;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javamicroservice.cloud.cluster.core.pojo.Instance;
import com.javamicroservice.cloud.cluster.core.pojo.Microservice;
import com.javamicroservice.cloud.cluster.core.pojo.Slave;
import com.javamicroservice.cloud.cluster.core.util.ApplicationProperties;

public class SlaveRepository {
	private static final String MICROSERVICE_REPO_DB;

	private static Slave slave;

	private static boolean initialized = false;

	private static ObjectMapper mapper = new ObjectMapper();

	static {
		MICROSERVICE_REPO_DB = ApplicationProperties.getPropery("db_path")
				+ "/microservice_repo.db";

		try {
			slave = mapper.readValue(new File(MICROSERVICE_REPO_DB),
					Slave.class);
			initialized = true;
		} catch (Exception e) {
			e.printStackTrace();
			initialized = false;
		}
		if (!initialized) {
			slave = new Slave();
			slave.setSlaveId(UUID.randomUUID().toString());
			slave.setClusterName(ApplicationProperties
					.getPropery("spring.application.name"));
			if (ApplicationProperties.getPropery("cluster_self_address") != null) {
				slave.setHostAddress(ApplicationProperties
						.getPropery("cluster_self_address"));
			} else {
				try {
					slave.setHostAddress("http://"
							+ InetAddress.getLocalHost().getHostAddress() + ":"
							+ ApplicationProperties.getPropery("server.port"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		initialized = true;
	}

	public static Slave getSlave() {
		return slave;
	}

	private static void saveRepo() {
		try {
			mapper.writeValue(new File(MICROSERVICE_REPO_DB), slave);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Microservice alreadyExists(Microservice microservice) {
		for (String key : slave.getMicroserviceMap().keySet()) {
			Microservice item = slave.getMicroserviceMap().get(key);
			if (item.getServiceAddress().equalsIgnoreCase(
					microservice.getServiceAddress())
					&& item.getFullPath().equalsIgnoreCase(
							microservice.getFullPath())
					&& item.getJarFileName().equalsIgnoreCase(
							microservice.getJarFileName())) {
				return item;
			}
		}
		return null;
	}

	private static Instance alreadyExists(Instance instance, String serviceId) {
		if (slave.getMicroserviceMap().containsKey(serviceId)) {
			for (String key : slave.getMicroserviceMap().get(serviceId)
					.getInstances().keySet()) {
				Instance item = slave.getMicroserviceMap().get(serviceId)
						.getInstances().get(key);
				if (instance.getPort() == item.getPort()) {
					return item;
				}
			}
		}
		return instance;
	}

	public static void addMicroservice(Microservice microservice) {
		Microservice checkExists = alreadyExists(microservice);
		if (checkExists == null) {
			microservice.setServiceId(UUID.randomUUID().toString());
			slave.getMicroserviceMap().put(microservice.getServiceId(),
					microservice);
		} else {
			Instance instance = alreadyExists(microservice.getInstances()
					.entrySet().iterator().next().getValue(),
					checkExists.getServiceId());
			checkExists.getInstances().put(instance.getInstanceId(), instance);
			slave.getMicroserviceMap().put(checkExists.getServiceId(),
					checkExists);
		}
		saveRepo();
	}

	public static Microservice getMicroservice(String id) {
		if (slave.getMicroserviceMap().containsKey(id)) {
			return slave.getMicroserviceMap().get(id);
		}
		return null;
	}
}
