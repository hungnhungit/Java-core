package com.banking.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.banking.demo.models.Message;

@Service
public class MessageService {

	private final String NAME = "messages";
	
	private Message message;
	
	public MessageService() {
		this.message = new Message();
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	public void addMessage(String message, String type,RedirectAttributes redirectAttributes) {
		this.message.setMessage(message);
		this.message.setType(type);
		redirectAttributes.addFlashAttribute(this.NAME,this.getMessage());
	}
	
	
}
