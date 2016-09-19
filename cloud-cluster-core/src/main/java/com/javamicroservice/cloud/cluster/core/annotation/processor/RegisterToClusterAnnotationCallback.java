package com.javamicroservice.cloud.cluster.core.annotation.processor;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.web.client.RestTemplate;

import com.javamicroservice.cloud.cluster.core.annotation.RegisterToClusterAnnotation;
import com.javamicroservice.cloud.cluster.core.pojo.Microservice;
import com.javamicroservice.cloud.cluster.core.util.ApplicationProperties;

public class RegisterToClusterAnnotationCallback implements MethodCallback {

	private ConfigurableListableBeanFactory configurableBeanFactory;
	private Object bean;

	public RegisterToClusterAnnotationCallback(
			ConfigurableListableBeanFactory configurableBeanFactory, Object bean) {
		this.configurableBeanFactory = configurableBeanFactory;
		this.bean = bean;
	}

	public void doWith(Method method) throws IllegalArgumentException,
			IllegalAccessException {
		if (!method.isAnnotationPresent(RegisterToClusterAnnotation.class)) {
			return;
		}

		RestTemplate restTemplate = new RestTemplate();
		Microservice microservice = new Microservice();
		microservice.setOnline(true);
		microservice.setServiceName(ApplicationProperties
				.getPropery("spring.application.name"));
		String registerUrl = ApplicationProperties.getPropery("cluster_url")
				+ "register";
		System.out.println("Cluster registration URL: " + registerUrl);
		try {
			microservice.setServiceAddress("http://"
					+ InetAddress.getLocalHost().getHostName() + ":"
					+ ApplicationProperties.getPropery("server.port"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		microservice.setFullPath(ApplicationProperties
				.getPropery(ApplicationProperties.EXECUTOR_FULL_PATH));
		try {
			microservice.setJarFileName(ApplicationProperties
					.getPropery(ApplicationProperties.EXECUTOR_NAME));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("executing jar: " + microservice.getJarFileName());
		HttpEntity<Microservice> requestEntity = new HttpEntity<Microservice>(
				microservice);
		try {
			ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
					registerUrl, HttpMethod.POST, requestEntity, Boolean.class);
			if (responseEntity.getBody()) {
				System.out.println("Registered with cluster "
						+ ApplicationProperties.getPropery("cluster_url"));
			}
		} catch (Exception e) {
			System.out.println("Could not register with cluster "
					+ ApplicationProperties.getPropery("cluster_url"));
			e.printStackTrace();
		}

	}
}
