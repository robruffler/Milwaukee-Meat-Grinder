package com.fauxwerd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "content")
public class Content {
	
	@Id 
	@GeneratedValue (generator = "CONTENT_TABLE_GEN")
	@TableGenerator (
			name = "CONTENT_TABLE_GEN",
			pkColumnValue = "CONTENT",
			allocationSize = 20
	)
	@Column(name = "content_id")
	private Long id;
	
	@NotNull
	@Size(min = 9, max = 2000)
	@NaturalId
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
