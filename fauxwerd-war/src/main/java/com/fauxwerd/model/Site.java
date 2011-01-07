package com.fauxwerd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "site")
public class Site {
	
	private Long id;	
	private String hostname;	
	private List<Content> content = new ArrayList<Content>();

	@Id 
	@GeneratedValue (generator = "SITE_TABLE_GEN")
	@TableGenerator (
			name = "SITE_TABLE_GEN",
			pkColumnValue = "SITE",
			allocationSize = 10
	)
	@Column(name = "site_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Column(columnDefinition = "varchar(255)")
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	@OneToMany(mappedBy = "site")	
	public List<Content> getContent() {
		return content;
	}

	public void setContent(List<Content> content) {
		this.content = content;
	}
}
