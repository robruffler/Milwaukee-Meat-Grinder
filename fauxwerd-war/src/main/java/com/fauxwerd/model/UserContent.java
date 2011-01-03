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
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -2579973513441057170L;

		@Column(name = "user_id")
		private Long userId;
		
		@Column(name = "content_id")
		private Long contentId;
		
		public Id() {}
		
		public Id(Long userId, Long contentId) {
			this.userId = userId;
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
	
	@EmbeddedId
	private Id id = new Id();

	@Column(name = "date_added")
	private Date dateAdded = new Date();
		
	@ManyToOne
	@JoinColumn(name = "user_id",
				insertable = false,
				updatable = false)
	@org.hibernate.annotations.ForeignKey(name = "FK_USER_CONTENT_USER")				
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "content_id",
				insertable = false,
				updatable = false)
	@org.hibernate.annotations.ForeignKey(name = "FK_USER_CONTENT_CONTENT")				
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
	
	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
	
}
