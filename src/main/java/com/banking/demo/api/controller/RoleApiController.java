package com.banking.demo.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.banking.demo.api.controller.response.model.StatusResponse;
import com.banking.demo.models.Post;
import com.banking.demo.models.Role;
import com.banking.demo.models.Tag;
import com.banking.demo.repositories.PostRepository;
import com.banking.demo.repositories.RoleRepository;
import com.banking.demo.repositories.TagRepository;
import com.banking.demo.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/api/roles")
public class RoleApiController extends AbstractBaseAPI{

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	@RequestMapping(value = {"","/"},method = RequestMethod.GET,produces = APIName.CHARSET)
	public String listRole() throws JsonProcessingException {
			List<Role> roles = roleRepository.findAll();
			return writeObjectToJson(new StatusResponse<>(HttpStatus.OK.value(),roles));
	}
//	@RequestMapping(value = {"/{id}"},method = RequestMethod.GET,produces = APIName.CHARSET)
//	public String getRole(@PathVariable("id") Integer id) throws JsonProcessingException {
//		return writeObjectToJson(new StatusResponse<>(HttpStatus.OK.value(),roleRepository.findOne(id)));
//	}
//	
	@RequestMapping(value = {"/{name}"},method = RequestMethod.GET,produces = APIName.CHARSET)
	public Role getRoleByName(@PathVariable("name") String name) {
		return roleRepository.testByName(name);
	}
	
	@RequestMapping(value = {"/delete/{id}"},method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteRole(@PathVariable("id") Integer id) {
		
		roleRepository.delete(id);
		
	}
	
	@RequestMapping(value = {"/update"},method = RequestMethod.PATCH)
	@ResponseBody
	public Role updateRole(@RequestBody Role role) {
		Role roleUpdate = roleRepository.findOne(role.getId());
		roleUpdate.setName(role.getName());
		return roleRepository.save(roleUpdate);
	}

}
