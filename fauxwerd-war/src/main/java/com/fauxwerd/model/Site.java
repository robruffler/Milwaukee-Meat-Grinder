package com.fauxwerd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.fauxwerd.util.HashCodeUtil;

@Entity
@Table(name = "site")
public class Site {
	
	private Long id;	
	private Integer version;
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
	
    @Version
    @Column(name="optlock")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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
	
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof Site)) return false;
		
		final Site site = (Site)other;
		if (!site.getHostname().equals( getHostname())) return false;
		
		return true;
	}
	
	public int hashCode() {
		int result = HashCodeUtil.SEED;

		result = HashCodeUtil.hash(result, getHostname());
		
		return result;		
	}
	
}
