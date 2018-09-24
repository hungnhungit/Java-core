package com.banking.demo.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErorrController {

	
	
	@RequestMapping(value = "/Access_Denied",method = RequestMethod.GET)
	public String access() {
		return "errors/accessDenied";
	}
	
}
