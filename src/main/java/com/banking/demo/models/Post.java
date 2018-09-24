package com.banking.demo.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.banking.demo.lib.Slug;

@Entity
@Table(name = "posts")
public class Post extends BaseModel {

	@NotBlank(message = "Name can't be empty.")
	private String name;
	@NotBlank(message = "Title can't be empty.")
	private String title;
	private String slug;
	@NotBlank(message = "Body can't be empty.")
	private String body;
	private int view;

	@OneToOne
	public Category category;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "posts_tags", joinColumns = { @JoinColumn(name = "post_id") }, inverseJoinColumns = {
			@JoinColumn(name = "tag_id") })
	public List<Tag> tags = new ArrayList<Tag>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public int compareTo(BaseModel o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return "Post [name=" + name + ", title=" + title + ", slug=" + slug + ", body=" + body + ", view=" + view + "]";
	}
	
	@PreUpdate
	public void prePersist() {
		slug = Slug.makeSlug(name);
	}
	
	
	 

}
