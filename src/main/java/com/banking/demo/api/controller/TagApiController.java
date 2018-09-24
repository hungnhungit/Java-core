package com.banking.demo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.banking.demo.api.controller.response.model.StatusResponse;
import com.banking.demo.models.Category;
import com.banking.demo.models.Tag;
import com.banking.demo.repositories.TagRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@Api(value = "tags API")
public class TagApiController extends AbstractBaseAPI{

	@Autowired
	TagRepository tagRepository;
	
	@ApiOperation(value = "getTags")
	@RequestMapping(value = APIName.TAGS, method = RequestMethod.GET, produces = APIName.CHARSET)
	public String listTag() throws JsonProcessingException {
		List<Tag> tags = (List<Tag>) tagRepository.findAll();
        return writeObjectToJson(new StatusResponse<>(HttpStatus.OK.value(), tags));
	}
	
}
