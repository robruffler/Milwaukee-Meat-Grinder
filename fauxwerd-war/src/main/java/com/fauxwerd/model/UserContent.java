package com.fauxwerd.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "user_content")
public class UserContent {
	
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
	private Date dateAdded = new Date();		
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
	
//	@PrePersist
//	protected void onCreate() {
//		dateAdded = new Date();
//	}	
	
	@EmbeddedId	
	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	@Column(name = "date_added")
	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
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
	
}
