package com.banking.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.demo.models.Menu;

public interface MenuRepository extends JpaRepository<Menu,Integer>{

}
