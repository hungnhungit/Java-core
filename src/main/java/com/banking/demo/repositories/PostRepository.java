package com.banking.demo.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.banking.demo.models.Menu;
import com.banking.demo.models.Post;

public interface PostRepository extends JpaRepository<Post,Integer>{
//	@Query(value = "SELECT p FROM posts")
//    List<Post> test();
	

	Page<Post> findAll(Pageable pageable);
	
	Page<Post> findByNameIsLikeOrTitleIsLike(String name, String title,Pageable pageable);
	
//	@Query(value = "SELECT p.id FROM Post p ORDER BY id")
//	Page<Post> tester(Pageable pageable);
}
