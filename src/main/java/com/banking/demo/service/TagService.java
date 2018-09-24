package com.banking.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.demo.models.Tag;
import com.banking.demo.repositories.TagRepository;

@Service
public class TagService {

	@Autowired
	private TagRepository tagRepository;
	
	public Iterable<Tag> findAll(){
		return tagRepository.findAll();
	}
	
}
