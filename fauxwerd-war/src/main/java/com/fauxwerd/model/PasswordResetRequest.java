package com.fauxwerd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fauxwerd.util.HashCodeUtil;

@Entity
@Table(name = "password_reset")
public class PasswordResetRequest {

	private Long id;
	private Integer version;
	private String code;	
	private User user;	
	private DateTime dateAdded = new DateTime();
	private DateTime dateUpdated;

	public PasswordResetRequest() {};
	
	public PasswordResetRequest(User user, String code) {
		this.user = user;
		this.code = code;
	}
 	
	@Id 
	@GeneratedValue (generator = "PASSWORD_RESET_TABLE_GEN")
	@TableGenerator (
			name = "PASSWORD_RESET_TABLE_GEN",
			pkColumnValue = "PASSWORD_RESET",
			allocationSize = 20
	)
	@Column(name = "password_reset_id")
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
	@Column(columnDefinition = "varchar(40)")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@ManyToOne()
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
		if (!(other instanceof PasswordResetRequest)) return false;
		
		final PasswordResetRequest resetRequest = (PasswordResetRequest)other;
		if (!resetRequest.getCode().equals( getCode())) return false;
		
		return true;
	}
	
	public int hashCode() {
		int result = HashCodeUtil.SEED;

		result = HashCodeUtil.hash(result, getCode());
		
		return result;		
	}		
		
}
