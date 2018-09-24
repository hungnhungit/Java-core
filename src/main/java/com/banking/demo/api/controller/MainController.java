package com.banking.demo.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@RequestMapping(value = { "/", "/login1" })
	public String staticResource(Model model) {
		return "login1";
	}

	@RequestMapping(value = "/{locale:en|fr|vi}/login2")
	public String login2(Model model) {
		return "login2";
	}
	
	@RequestMapping(value = "/tester")
	@ResponseBody
	public String tester(HttpServletRequest request) {
		return "";
	}
}
