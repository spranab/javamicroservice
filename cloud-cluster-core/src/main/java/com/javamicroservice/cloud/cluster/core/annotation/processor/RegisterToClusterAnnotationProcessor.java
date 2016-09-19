package com.javamicroservice.cloud.cluster.core.annotation.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class RegisterToClusterAnnotationProcessor implements BeanPostProcessor {

	private ConfigurableListableBeanFactory configurableBeanFactory;

	@Autowired
	public RegisterToClusterAnnotationProcessor(
			ConfigurableListableBeanFactory beanFactory) {
		this.configurableBeanFactory = beanFactory;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		Class<?> managedBeanClass = bean.getClass();
		RegisterToClusterAnnotationCallback fieldCallback = new RegisterToClusterAnnotationCallback(
				configurableBeanFactory, bean);
		ReflectionUtils.doWithMethods(managedBeanClass, fieldCallback);
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

}
