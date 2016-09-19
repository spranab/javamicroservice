package com.javamicroservice.cloud.cluster.slave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javamicroservice.cloud.cluster.slave.cache.ClusterInfoCache;
import com.javamicroservice.cloud.cluster.slave.repo.SlaveRepository;

@Controller
public class SlaveController {

	@RequestMapping(value = "/")
	public String info(Model model) {
		model.addAttribute("slave", SlaveRepository.getSlave());
		model.addAttribute("uptime", ClusterInfoCache.getUpTime());
		return "info";
	}
}
