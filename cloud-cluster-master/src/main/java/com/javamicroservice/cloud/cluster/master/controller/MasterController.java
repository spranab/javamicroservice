package com.javamicroservice.cloud.cluster.master.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javamicroservice.cloud.cluster.master.cache.ClusterInfoCache;
import com.javamicroservice.cloud.cluster.master.cache.MasterCache;

@Controller
public class MasterController {

	@RequestMapping(value = "/")
	public String info(Model model) {
		model.addAttribute("master", MasterCache.getMaster());
		model.addAttribute("uptime", ClusterInfoCache.getUpTime());
		return "info";
	}
}
