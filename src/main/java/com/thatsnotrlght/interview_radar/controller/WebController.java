package com.thatsnotrlght.interview_radar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.thatsnotrlght.interview_radar.repository.CompanyRepository;
import com.thatsnotrlght.interview_radar.service.GhostService;

@Controller
public class WebController {
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private GhostService ghostService;
	
	@GetMapping("/")
	public String viewDashboard(Model model) {
		model.addAttribute("companies", companyRepository.findAll());
		
		return "index";
	}
}
