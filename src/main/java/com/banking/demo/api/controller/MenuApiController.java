package com.banking.demo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.banking.demo.models.Menu;
import com.banking.demo.repositories.MenuRepository;

@Controller
@RequestMapping(value = "api/menus")
public class MenuApiController {

	@Autowired
	MenuRepository menuRepository;
	
	@GetMapping({"","/"})
	@ResponseBody
	public List<Menu> listMenus() {
		return (List<Menu>)menuRepository.findAll();
	}
	
}
