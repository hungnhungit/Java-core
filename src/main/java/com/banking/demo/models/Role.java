package com.banking.demo.models;

import javax.persistence.*;


@Entity
@Table(name = "roles")
public class Role extends BaseModel {

	private String name;

	public String getName() {
		return name;
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