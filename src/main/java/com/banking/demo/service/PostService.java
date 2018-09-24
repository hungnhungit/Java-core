package com.banking.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.demo.models.Post;
import com.banking.demo.repositories.PostRepository;

@Service
@Transactional
public class PostService {

	
	@Autowired
	PostRepository postRepository;
	@Transactional
	public Page<Post> findAll(Pageable pageable) {
		return postRepository.findAll(pageable);
}
	
}
