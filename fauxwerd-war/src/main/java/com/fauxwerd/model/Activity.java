package com.fauxwerd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fauxwerd.util.HashCodeUtil;

@Entity
@Table(name = "activity")
public class Activity {
	
	private Long id;
	private Integer version;
	private User user;
	private ActivityType type;
	//TODO consider replacing content with serialized json object if we want to track activity on more than just Content
	private Content content;
	private DateTime dateAdded = new DateTime();
	private DateTime dateUpdated;

	public Activity() {}
	
	public Activity(User user, ActivityType type, Content content) {
		this.user = user;
		this.type = type;
		this.content = content;
	}
	
	@Id 
	@GeneratedValue (generator = "ACTIVITY_TABLE_GEN")
	@TableGenerator (
			name = "ACTIVITY_TABLE_GEN",
			pkColumnValue = "ACTIVITY",
			allocationSize = 20
	)
	@Column(name = "activity_id")
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
	
	@OneToOne
	@JoinColumn(name = "user_id")	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@NotNull
	@Enumerated
	public ActivityType getType() {
		return type;
	}
	public void setType(ActivityType type) {
		this.type = type;
	}
			
	@OneToOne
	@JoinColumn(name = "content_id")
	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
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
		if (!(other instanceof Activity)) return false;
		
		final Activity activity = (Activity)other;
		if (!activity.getUser().equals( getUser())) return false;
		if (!activity.getType().equals( getType())) return false;
		if (!activity.getContent().equals( getContent())) return false;
		if (!activity.getDateAdded().equals( getDateAdded())) return false;
		
		return true;
	}
	
	public int hashCode() {
		int result = HashCodeUtil.SEED;

		result = HashCodeUtil.hash(result, getUser());
		result = HashCodeUtil.hash(result, getType());
		result = HashCodeUtil.hash(result, getContent());
		result = HashCodeUtil.hash(result, getDateAdded());
		
		return result;		
	}	
}
