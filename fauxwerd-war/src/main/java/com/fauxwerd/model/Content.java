package com.fauxwerd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "content")
public class Content {
	@Id @GeneratedValue
	@Column(name = "content_id")
	private Long id;
	
	private String url;
	
	@OneToOne(optional=true)
	@JoinTable(name="user_content",
        joinColumns = {
			@JoinColumn(name="content_id", unique = true)           
		},
		inverseJoinColumns = {
			@JoinColumn(name="user_id")
		}     
	)	
	private User user;
	
	//empty constructor required by hibernate
	public Content() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
