package com.fauxwerd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
//	TODO re-enable NaturalId (breaks hbm2ddl on mysql)
//	@NaturalId
	@Column(columnDefinition = "varchar(2000)")
	private String url;
	
	@NotNull
	@Enumerated
	private ContentStatus status;
	
	@Column(name = "raw_html_path")
	private String rawHtmlPath;
	
	@OneToMany(mappedBy = "content")
	private List<UserContent> userContent = new ArrayList<UserContent>();
	
	@ManyToOne
	@JoinTable(name = "site_content",
				joinColumns = { @JoinColumn(name = "content_id")},
				inverseJoinColumns = { @JoinColumn(name = "site_id")})
	@org.hibernate.annotations.ForeignKey(name = "FK_SITE_CONTENT_SITE",
											inverseName = "FK_SITE_CONTENT_CONTENT")				
	private Site site;
	
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
	
	public ContentStatus getStatus() {
		return status;
	}

	public void setStatus(ContentStatus status) {
		this.status = status;
	}
	
	public String getRawHtmlPath() {
		return rawHtmlPath;
	}

	public void setRawHtmlPath(String rawHtmlPath) {
		this.rawHtmlPath = rawHtmlPath;
	}

	public List<UserContent> getUserContent() {
		return userContent;
	}

	public void setUserContent(List<UserContent> userContent) {
		this.userContent = userContent;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
		
}
