package com.fauxwerd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "invite")
public class Invite {
	
	private Long id;
	private Integer version;
	private String code;
	private InviteStatus status;
	private User invitedUser;	
	private DateTime dateAdded = new DateTime();
	private DateTime dateUpdated;
	
	public Invite() {}
	
	public Invite(String code) {
		this.code = code;
		this.status = InviteStatus.ACTIVE;
	}

	@Id 
	@GeneratedValue (generator = "INVITE_TABLE_GEN")
	@TableGenerator (
			name = "INVITE_TABLE_GEN",
			pkColumnValue = "INVITE",
			allocationSize = 20
	)
	@Column(name = "invite_id")
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

	@NotNull
	@Enumerated
	public InviteStatus getStatus() {
		return status;
	}
	public void setStatus(InviteStatus status) {
		this.status = status;
	}
	
	@OneToOne(mappedBy = "invite")
	public User getInvitedUser() {
		return invitedUser;
	}

	public void setInvitedUser(User invitedUser) {
		this.invitedUser = invitedUser;
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

}
