package com.banking.demo.models;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseModel {

	@Column(unique = true)
	private String name;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "parent_id")
	private Category parentId;

	public Category getParentId() {
		return this.parentId;
	}

	public void setParentId(Category parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public int compareTo(BaseModel o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
