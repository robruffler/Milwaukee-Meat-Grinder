package com.fauxwerd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

//TODO implement equals and hashCode methods for all Entities for comparison when detatched
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
//	TODO re-enable NaturalId (breaks hbm2ddl on mysql)
//	@NaturalId(mutable = true)
	@Column(columnDefinition="varchar(256)")
	private String email;
	
	@NotNull
	private boolean enabled;	

	@OneToMany(mappedBy = "user")
	@OrderBy("dateAdded desc")
	private List<UserContent> userContent = new ArrayList<UserContent>();
	
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
	
	public List<UserContent> getUserContent() {
		return userContent;
	}

	public void setUserContent(List<UserContent> userContent) {
		this.userContent = userContent;
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
