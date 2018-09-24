package com.banking.demo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.banking.demo.models.Category;
import com.banking.demo.repositories.CategoryRepository;

@Controller
@RequestMapping(value = "api/category")
public class CategoryApiController {

	
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping(value = {"","/"})
	@ResponseBody
	public List<Category> ApiCategory() {
		return (List<Category>)categoryRepository.findAll();
	}
	
}
