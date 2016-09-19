package com.javamicroservice.cloud.cluster.master.cache;

import com.javamicroservice.cloud.cluster.core.pojo.Master;
import com.javamicroservice.cloud.cluster.core.pojo.Slave;
import com.javamicroservice.cloud.cluster.core.util.ApplicationProperties;

public class MasterCache {
	private static Master master;

	static {
		master = new Master();
		master.setMasterId(1);
		master.setMasterName(ApplicationProperties
				.getPropery("spring.application.name"));
	}

	public static Master getMaster() {
		return master;
	}

	private static Slave checkExists(Slave slave) {
		for (String key : master.getSlaveMap().keySet()) {
			Slave item = master.getSlaveMap().get(key);
			if (item.getClusterName().equalsIgnoreCase(slave.getClusterName())
					&& item.getHostAddress().equalsIgnoreCase(
							slave.getHostAddress())) {
				return item;
			}
		}
		return null;
	}

	public static Slave getSlave(String id) {
		if (master.getSlaveMap().containsKey(id)) {
			return master.getSlaveMap().get(id);
		}
		return null;
	}

	public static void addSlave(Slave slave) {
		Slave checkExists = checkExists(slave);
		if (checkExists != null) {
			slave.setSlaveId(checkExists.getSlaveId());
		}
		master.getSlaveMap().put(slave.getSlaveId(), slave);

	}
}
