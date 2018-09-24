package com.banking.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	
	@GetMapping("/admin/dashboard")
	public String admin() {
		return "admin/dashboard";
	}
	
}
