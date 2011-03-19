package com.fauxwerd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fauxwerd.util.HashCodeUtil;

@Entity
@Table(name = "content")
public class Content {
	
	private Long id;
	private Integer version;
	private String url;
	private String title;
	private ContentStatus status;
	private String rawHtmlPath;	
	private String parsedHtmlPath;
//	private Long upVotes;
//	private Long downVotes;
	private List<UserContent> userContent = new ArrayList<UserContent>();
	private List<Topic> topics = new ArrayList<Topic>();
	private Site site;
	private DateTime dateAdded = new DateTime();
	private DateTime dateUpdated;
		
	//empty constructor required by hibernate
	public Content() { }

	@Id 
	@GeneratedValue (generator = "CONTENT_TABLE_GEN")
	@TableGenerator (
			name = "CONTENT_TABLE_GEN",
			pkColumnValue = "CONTENT",
			allocationSize = 20
	)
	@Column(name = "content_id")
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
	@Size(min = 9, max = 2000)
//	TODO re-enable NaturalId (breaks hbm2ddl on mysql)
//	@NaturalId
	@Column(columnDefinition = "varchar(2000)")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotNull
	@Enumerated
	public ContentStatus getStatus() {
		return status;
	}

	public void setStatus(ContentStatus status) {
		this.status = status;
	}
	
	@Column(name = "raw_html_path")
	public String getRawHtmlPath() {
		return rawHtmlPath;
	}

	public void setRawHtmlPath(String rawHtmlPath) {
		this.rawHtmlPath = rawHtmlPath;
	}
	
	@Column(name = "parsed_html_path")
	public String getParsedHtmlPath() {
		return parsedHtmlPath;
	}
	
	public void setParsedHtmlPath(String parsedHtmlPath) {
		this.parsedHtmlPath = parsedHtmlPath;
	}
	
//	@Column(name = "up_votes")
//	public Long getUpVotes() {
//		return upVotes;
//	}
//
//	public void setUpVotes(Long upVotes) {
//		this.upVotes = upVotes;
//	}
//
//	@Column(name = "down_votes")
//	public Long getDownVotes() {
//		return downVotes;
//	}
//
//	public void setDownVotes(Long downVotes) {
//		this.downVotes = downVotes;
//	}

	@OneToMany(mappedBy = "content")
	public List<UserContent> getUserContent() {
		return userContent;
	}

	public void setUserContent(List<UserContent> userContent) {
		this.userContent = userContent;
	}
	
	@ManyToMany
	@JoinTable(name = "content_topic",
				joinColumns = { @JoinColumn(name = "content_id")},
				inverseJoinColumns = { @JoinColumn(name = "topic_id")})
	@org.hibernate.annotations.ForeignKey(name = "FK_CONTENT_TOPIC_TOPIC",
											inverseName = "FK_CONTENT_TOPIC_CONTENT")
	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	@ManyToOne
	@JoinTable(name = "site_content",
				joinColumns = { @JoinColumn(name = "content_id")},
				inverseJoinColumns = { @JoinColumn(name = "site_id")})
	@org.hibernate.annotations.ForeignKey(name = "FK_SITE_CONTENT_SITE",
											inverseName = "FK_SITE_CONTENT_CONTENT")					
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	
	@Column(name = "date_added")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(DateTime dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	@Column(name = "date_updated")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(DateTime dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
		
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof Content)) return false;
		
		final Content content = (Content)other;
		if (!content.getUrl().equals( getUrl())) return false;
		
		return true;
	}
	
	public int hashCode() {
		int result = HashCodeUtil.SEED;

		result = HashCodeUtil.hash(result, getUrl());
		
		return result;		
	}
		
}
