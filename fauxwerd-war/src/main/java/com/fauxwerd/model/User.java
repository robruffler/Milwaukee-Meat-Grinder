package com.fauxwerd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User implements UserDetails {

	private static final long serialVersionUID = 1251530774635471804L;

	@Id 
	@GeneratedValue(generator = "USER_TABLE_GEN")
	@TableGenerator(
			name = "USER_TABLE_GEN",
			pkColumnValue = "USER",
			allocationSize = 5
	)
	@Column(name = "user_id")
	private Long id;
	
	@NotNull
	@Size(min=1, max=50)
	private String password;
		
	@NotNull
	@Pattern(regexp="(\\w+)@(\\w+\\.)(\\w+)(\\.\\w+)*")
	@Size(min=5, max=256)
	@NaturalId(mutable = true)
	private String email;
	
	@NotNull
	private boolean enabled;	
	
//TODO read about JoinTables, determine if unique = true should stay
	@OneToMany
	@JoinTable(
		name = "user_content",
		joinColumns = {@JoinColumn(name="user_id"/*, unique = true*/)},
		inverseJoinColumns = {@JoinColumn(name="content_id")}
	)
	@org.hibernate.annotations.ForeignKey(
		name = "FK_USER_ID",
		inverseName = "FK_CONTENT_ID"
	)
	private List<Content> content;

	//empty constructor required by hibernate
	public User() { }
	
	public User(String password, String email, boolean enabled) {
		
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		
	}	

	public Long getId() {
		return id;
	}

// This setter should never be used, id is set by hibernate
//	public void setId(Long id) {
//		this.id = id;
//	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
		
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public List<Content> getContent() {
		return content;
	}

	public void setContent(List<Content> content) {
		this.content = content;
	}
	
	//required by interface, returning email address instead
	public String getUsername() {
		return email;
	}
	
	//these booleans are required by UserDetails interface, using enabled state for all
	public boolean isAccountNonExpired() {
		return enabled;
	}

	public boolean isAccountNonLocked() {
		return enabled;
	}

	public boolean isCredentialsNonExpired() {
		return enabled;
	}

	//currently only one user role so we're always returning ROLE_USER
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));

		return authorities;
	}
	
	public void fullyEnable() {
		enabled = true;
	}
	
	public String toString() {
		return String.format("id = [%s], email = [%s], password = [%s]", this.id, this.email, this.password);
	}
	
}
