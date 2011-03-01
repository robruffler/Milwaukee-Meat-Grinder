package com.fauxwerd.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

import org.joda.time.DateTime;

import com.fauxwerd.util.HashCodeUtil;

@Entity
@Table(name = "user_content")
public class UserContent implements Comparable<UserContent> {
	
	@Embeddable
	public static class Id implements Serializable {
		
		private static final long serialVersionUID = -2579973513441057170L;


		private Long userId;		
		private Long contentId;
		
		public Id() {}
		
		public Id(Long userId, Long contentId) {
			this.userId = userId;
			this.contentId = contentId;
		}
	
		@Column(name = "user_id")				
		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		@Column(name = "content_id")
		public Long getContentId() {
			return contentId;
		}

		public void setContentId(Long contentId) {
			this.contentId = contentId;
		}

		public boolean equals(Object o) {
			if (o != null && o instanceof Id) {
				Id that = (Id)o;
				return this.userId.equals(that.userId) &&
						this.contentId.equals(that.contentId);
			}
			else {
				return false;
			}
		}
		
		public int hashCode() {
			return userId.hashCode() + contentId.hashCode();
		}
	}
	
	
	private Id id = new Id();
	private Integer version;
	private DateTime dateAdded = new DateTime();
	private DateTime dateUpdated;
	private User user;	
	private Content content;
	
	public UserContent() {}
	
	public UserContent(User user, Content content) {
		// Set fields
		this.user = user;
		this.content = content;
		
		// Set identifier values
		this.id.userId = user.getId();
		this.id.contentId = content.getId();
		
		// Guarantee referential integrity
		user.getUserContent().add(this);
		content.getUserContent().add(this);
	}
	
//TODO figure out how this is supposed to work
//	@PrePersist
//	protected void onCreate() {
//		dateAdded = new PersistentDateTime();
//	}	
	
	@EmbeddedId	
	public Id getId() {
		return id;
	}

	public void setId(Id id) {
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

	@ManyToOne
	@JoinColumn(name = "user_id",
				insertable = false,
				updatable = false)
	@org.hibernate.annotations.ForeignKey(name = "FK_USER_CONTENT_USER")				
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "content_id",
				insertable = false,
				updatable = false)
	@org.hibernate.annotations.ForeignKey(name = "FK_USER_CONTENT_CONTENT")					
	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof UserContent)) return false;
		
		final UserContent userContent = (UserContent)other;
		if (!userContent.getId().equals( getId())) return false;
		if (!userContent.getDateAdded().equals( getDateAdded())) return false;
		
		return true;
	}
	
	public int hashCode() {
		int result = HashCodeUtil.SEED;

		result = HashCodeUtil.hash(result, getId());
		result = HashCodeUtil.hash(result, getDateAdded());
		
		return result;		
	}
	//natural sort order sorts by most recent dateUpdated, falling back to dateCreated if updated is null
	@Override
	public int compareTo(UserContent o) {
		int comparison = 0;
		
		//check if this item has a dateUpdated
		if (this.getDateUpdated() != null) {
			//check if object we're comparing to has an dateUpdated
			if (o.getDateUpdated() != null) {
				//compare dateUpdated fields
				comparison = this.getDateUpdated().compareTo(o.getDateUpdated());
				
			}
			else {
				//compare this dateUpdated to that dateAdded
				comparison = this.getDateUpdated().compareTo(o.getDateAdded());
			}
		}
		else {
			//check if object we're comparing to has an dateUpdated
			if (o.getDateUpdated() != null) {
				//compare this dateAdded to that dateUpdated
				comparison = this.getDateAdded().compareTo(o.getDateUpdated());
			}
			else {
				//compare dateAdded fields
				comparison = this.getDateAdded().compareTo(o.getDateAdded());
			}			
		}
		//negating comparison to sort in descending order
		return -comparison;
	}
	
	
}
