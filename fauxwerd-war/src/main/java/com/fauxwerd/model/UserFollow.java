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

import com.fauxwerd.model.UserContent.Id;
import com.fauxwerd.util.HashCodeUtil;

@Entity
@Table(name = "user_follow")
public class UserFollow {
	
	@Embeddable
	public static class Id implements Serializable {		
		private static final long serialVersionUID = -944500145687838827L;
		private Long userId;		
		private Long followId;
		
		public Id() {}
		
		public Id(Long userId, Long followId) {
			this.userId = userId;
			this.followId = followId;
		}
	
		@Column(name = "user_id")				
		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		@Column(name = "follow_id")
		public Long getFollowId() {
			return followId;
		}

		public void setFollowId(Long followId) {
			this.followId = followId;
		}

		public boolean equals(Object o) {
			if (o != null && o instanceof Id) {
				Id that = (Id)o;
				return this.userId.equals(that.userId) &&
						this.followId.equals(that.followId);
			}
			else {
				return false;
			}
		}
		
		public int hashCode() {
			return userId.hashCode() + followId.hashCode();
		}
	}	
	
	private Id id = new Id();
	private Integer version;
	private DateTime dateAdded = new DateTime();
	private DateTime dateUpdated;
	private User user;
	private User follow;
	
	public UserFollow() {}
	
	public UserFollow(User user, User follow) {
		// Set fields
		this.user = user;
		this.follow = follow;
		
		// Set identifier values
		this.id.userId = user.getId();
		this.id.followId = follow.getId();
		
		// Guarantee referential integrity
		user.getFollowing().add(this);
		follow.getFollowers().add(this);
	}
	
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
	@org.hibernate.annotations.ForeignKey(name = "FK_USER_FOLLOW_USER")				
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "follow_id",
				insertable = false,
				updatable = false)
	@org.hibernate.annotations.ForeignKey(name = "FK_USER_FOLLOW_FOLLOW")					
	public User getFollow() {
		return follow;
	}

	public void setFollow(User follow) {
		this.follow = follow;
	}
	
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof UserFollow)) return false;
		
		final UserFollow userFollow = (UserFollow)other;
		if (!userFollow.getUser().equals( getUser())) return false;
		if (!userFollow.getFollow().equals( getFollow())) return false;
		
		return true;
	}
	
	public int hashCode() {
		int result = HashCodeUtil.SEED;

		result = HashCodeUtil.hash(result, getUser());
		result = HashCodeUtil.hash(result, getFollow());
		
		return result;		
	}	
	
}
