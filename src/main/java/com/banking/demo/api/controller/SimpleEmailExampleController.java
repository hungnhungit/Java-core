package com.banking.demo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banking.demo.config.Constants;


@Controller
public class SimpleEmailExampleController {

//	@Autowired
//	public JavaMailSender emailSender;
//
//	@ResponseBody
//	@RequestMapping("/sendMail")
//	public String sendSimpleEmail() {
//
//		// Create a Simple MailMessage.
//		SimpleMailMessage message = new SimpleMailMessage();
//
//		message.setTo(Constants.MY_EMAIL);
//		message.setSubject("Test Simple Email");
//		message.setText("Hello, Im testing Simple Email");
//
//		// Send Message!
//		this.emailSender.send(message);
//
//		return "Email Sent!";
//	}

}
