package com.fauxwerd.model;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
		
	private String password;
	private boolean enabled;	
	
	@NotNull
	@Size(min=5, max=256)
	private String email;
	
	@OneToMany
	@JoinTable(name = "user_content",
	  joinColumns = {
		@JoinColumn(name="user_id", unique = true)           
	  },
	  inverseJoinColumns = {
	    @JoinColumn(name="content_id")
	  }
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

	public void setId(Long id) {
		this.id = id;
	}

	//currently only one user role so we're always returning ROLE_USER
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));

		return authorities;
	}
	
	public String getPassword() {
		return password;
	}

	//required by interface, returning email address instead
	public String getUsername() {
		return email;
	}
	
	//these booleans are required by interface, using enabled state for all
	public boolean isAccountNonExpired() {
		return enabled;
	}

	public boolean isAccountNonLocked() {
		return enabled;
	}

	public boolean isCredentialsNonExpired() {
		return enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Content> getContent() {
		return content;
	}

	public void setContent(List<Content> content) {
		this.content = content;
	}

	public void fullyEnable() {
		//accountNonExpired = true;
		//credentialsNonExpired = true;
		enabled = true;
		//accountNonLocked = true;
	}
	
	public String toString() {
		return String.format("id = [%s], email = [%s], password = [%s]", this.id, this.email, this.password);
	}
	
}
