package com.banking.demo.models;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.persistence.*;



@MappedSuperclass
public abstract class BaseModel implements Comparable<BaseModel>, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	
	@Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@PrePersist
	public void prePersist() {
		createdAt = updatedAt = new Date();
	}
	
	@PreUpdate
	public void prePersistUpdate() {
		updatedAt = new Date();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
