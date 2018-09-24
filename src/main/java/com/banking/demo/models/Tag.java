package com.banking.demo.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tags")
public class Tag extends BaseModel {

	@Basic(optional = false)
	@Column(name = "name")
	private String name;

	@ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
	private List<Post> posts = new ArrayList<>();

	public Tag() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public int compareTo(BaseModel o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
