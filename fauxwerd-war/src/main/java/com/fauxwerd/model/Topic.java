package com.fauxwerd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fauxwerd.util.HashCodeUtil;

@Entity
@Table(name = "topic")
public class Topic {

	private Long id;
	private Integer version;
	private String name;
	private List<Topic> parents = new ArrayList<Topic>();
	private List<Topic> children = new ArrayList<Topic>();
	private List<Content> content = new ArrayList<Content>();
	private DateTime dateAdded = new DateTime();
	private DateTime dateUpdated;

	public Topic() {}	
	
	public Topic(String name) {
		this.name = name;
	}
	
	@Id 
	@GeneratedValue (generator = "TOPIC_TABLE_GEN")
	@TableGenerator (
			name = "TOPIC_TABLE_GEN",
			pkColumnValue = "TOPIC",
			allocationSize = 5
	)
	@Column(name = "topic_id")	
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
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany
	@JoinTable(name = "topic_relation",
				joinColumns = { @JoinColumn(name = "topic_id")},
				inverseJoinColumns = { @JoinColumn(name = "parent_id")})
	@org.hibernate.annotations.ForeignKey(name = "FK_TOPIC_RELATION_PARENT",
											inverseName = "FK_TOPIC_RELATION_TOPIC")						
	public List<Topic> getParents() {
		return parents;
	}
	public void setParents(List<Topic> parents) {
		this.parents = parents;
	}
	
	@ManyToMany(mappedBy = "parents")
	public List<Topic> getChildren() {
		return children;
	}
	public void setChildren(List<Topic> children) {
		this.children = children;
	}
		
	@ManyToMany(mappedBy = "topics")
	@OrderBy(value = "dateAdded desc")
	public List<Content> getContent() {
		return content;
	}

	public void setContent(List<Content> content) {
		this.content = content;
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
		if (!(other instanceof Topic)) return false;
		
		final Topic topic = (Topic)other;
		if (!topic.getName().equals( getName())) return false;
		
		return true;
	}
	
	public int hashCode() {
		int result = HashCodeUtil.SEED;

		result = HashCodeUtil.hash(result, getName());
		
		return result;		
	}	
	
}
