package com.banking.demo.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.banking.demo.config.AppConfig;
import com.banking.demo.utils.Constant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.*;

public class AbstractBaseAPI {
	public final static ObjectMapper mapper = new ObjectMapper();

	
	Gson gson = new GsonBuilder().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat(Constant.API_FORMAT_DATE).create();

	
//	static {
//		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES)
//				.setSerializationInclusion(JsonInclude.Include.ALWAYS)
//				.setDateFormat(new SimpleDateFormat(Constant.API_FORMAT_DATE));
//	}

	@Autowired
	AppConfig appConfig;

	protected String writeObjectToJson(Object obj) throws JsonProcessingException{

		return mapper.writeValueAsString(obj);

	}
}
