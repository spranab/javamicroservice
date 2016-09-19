package com.javamicroservice.cloud.cluster.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ApplicationProperties {
	private static Properties properties = new Properties();

	public static final String EXECUTOR_BASE_PATH = "javamicroservice_executor_app_base_path";
	static {
		try {
			System.out.println("Started loading property files...");
			System.out.println("Current Thread: "
					+ Thread.currentThread().getName());
			// String basePath = System.getProperty("user.dir");
			String basePath = null;
			try {
				basePath = Thread.currentThread().getContextClassLoader()
						.getResource("").getPath();
			} catch (Exception e) {
				String coreJarPath = ApplicationProperties.class
						.getProtectionDomain().getCodeSource().getLocation()
						.toString();
				String[] locPart = coreJarPath.split("!");
				String parentJarPart = locPart[0];
				String parentJarFile = parentJarPart.replaceAll("jar:file:/",
						"");
				// File file = new File(parentJarFile);
				basePath = parentJarFile;
			}
			properties.put(EXECUTOR_BASE_PATH, basePath);

			System.out.println("Looking for property files from base path: "
					+ basePath);
			final File folder = new File(basePath);
			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.getName().endsWith(".properties")) {
					Properties temp = new Properties();
					temp.load(new FileInputStream(fileEntry));
					properties.putAll(temp);
					System.out.println("Loaded property file: "
							+ fileEntry.getPath() + "/" + fileEntry.getName());
				}
			}
			System.out
					.println("Completed loading property files. Total properties: "
							+ properties.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getPropery(String propertyName) {
		if (properties.containsKey(propertyName)) {
			return properties.getProperty(propertyName);
		}
		return null;
	}
}
