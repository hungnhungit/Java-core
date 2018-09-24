package com.banking.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.banking.demo.models.Tag;

public interface TagRepository extends PagingAndSortingRepository<Tag,Integer>{

}
