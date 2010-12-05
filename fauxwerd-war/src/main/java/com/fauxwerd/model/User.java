package com.fauxwerd.model;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User implements UserDetails {

	private static final long serialVersionUID = 1251530774635471804L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	
/*	
 * 	leaving this as an example of how to map one-to-may using hibernate
 *  may need to revisit this at some point if we introduce multiple authentication roles
	@ElementCollection
	@CollectionTable(
	  name = "authority",
	  joinColumns = @JoinColumn(name="user_id")
	)
	@Column(name = "authority")
	@Transient
	private List<GrantedAuthority> authorities;
*/
	
	private String password;

/*	
	@Transient
	private boolean accountNonExpired;
	@Transient
	private boolean accountNonLocked;
	@Transient
	private boolean credentialsNonExpired;
*/	
	private boolean enabled;	
	
	@NotNull
	@Size(min=5, max=256)
	private String email;

	public User() { }
	
	public User(List<GrantedAuthority> authorities, String password, String username, boolean accountNonExpired, 
			boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
		
//		this.authorities = authorities;
		this.password = password;
		//this.username = username;
//		this.accountNonExpired = accountNonExpired;
//		this.accountNonLocked = accountNonLocked;
//		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

/*	
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}
*/
	//currently only one user role so we're always returning ROLE_USER
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));

		return authorities;
	}
	
	public String getPassword() {
		return password;
	}

	public String getUsername() {
		//return username;
		//required by interface, returning email address instead
		return email;
	}

	public boolean isAccountNonExpired() {
		return enabled;
		//return accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return enabled;
		//return accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return enabled;
		//return credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}
/*
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
*/
	public void setPassword(String password) {
		this.password = password;
	}
/*
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
*/
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void fullyEnable() {
		//accountNonExpired = true;
		//credentialsNonExpired = true;
		enabled = true;
		//accountNonLocked = true;
	}
	
	public String toString() {
		//return String.format("username = [%s], email = [%s], password = [%s]", this.username, this.email, this.password);
		return String.format("id = [%s], email = [%s], password = [%s]", this.id, this.email, this.password);
	}
	
}
