package com.banking.demo.api.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.banking.demo.models.Menu;
import com.banking.demo.models.Product;
import com.banking.demo.repositories.MenuRepository;
import com.banking.demo.repositories.ProductRepository;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "v1/api/products")
public class ProductApiController {

	
	
	@Autowired
	ProductRepository productRepository;
	
	@RequestMapping(value = {"/",""},method = RequestMethod.GET, produces = APIName.CHARSET)
	public String getProducts() throws JsonGenerationException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(productRepository.findAll());
	}
	
	@RequestMapping(value = {"/{name}"},method = RequestMethod.GET, produces = APIName.CHARSET)
	public Product getProductsByName(@PathVariable("name") String name) {
		return productRepository.findByName(name);
	}
	
}
