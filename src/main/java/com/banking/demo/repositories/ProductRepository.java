package com.banking.demo.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.banking.demo.models.Category;
import com.banking.demo.models.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>{
	Product findByName(String name);
}
