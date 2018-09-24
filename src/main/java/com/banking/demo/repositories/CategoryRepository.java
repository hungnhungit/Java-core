package com.banking.demo.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.banking.demo.models.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>{

	Page<Category> findByNameIsLike(String name,Pageable pageable);
}
