package com.fauxwerd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.fauxwerd.util.HashCodeUtil;

@Entity
@Table(name = "user")
public class User implements UserDetails {

	private static final long serialVersionUID = 1251530774635471804L;

	private Long id;	
	private Integer version;
	private String password;		
	private String email;
	private boolean enabled;
	private List<Role> roles = new ArrayList<Role>();
	private SortedSet<UserContent> userContent = new TreeSet<UserContent>();
	private String fullName;
	private Invite invite;
	
	//empty constructor required by hibernate
	public User() { }
	
	public User(String password, String email, boolean enabled, String fullName) {		
		this.password = password;
		this.email = email;
		this.enabled = enabled;		
		this.fullName = fullName;
	}	

	@Id 
	@GeneratedValue(generator = "USER_TABLE_GEN")
	@TableGenerator(
			name = "USER_TABLE_GEN",
			pkColumnValue = "USER",
			allocationSize = 5,
			initialValue = 1000
	)
	@Column(name = "user_id")	
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
	@Size(min=1, max=50)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Pattern(regexp="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", flags={Pattern.Flag.CASE_INSENSITIVE})
//	TODO re-enable NaturalId (breaks hbm2ddl on mysql)
//	@NaturalId(mutable = true)
	@Column(columnDefinition="varchar(256)")	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@NotNull	
	public boolean isEnabled() {
		return enabled;
	}
		
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
				joinColumns = { @JoinColumn(name = "user_id")},
				inverseJoinColumns = { @JoinColumn(name = "role_id")})
	@org.hibernate.annotations.ForeignKey(name = "FK_USER_ROLE_USER",
											inverseName = "FK_USER_ROLE_ROLE")					
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(mappedBy = "user")
	@Sort(type = SortType.NATURAL)
	public SortedSet<UserContent> getUserContent() {	
		return userContent;
	}

	public void setUserContent(SortedSet<UserContent> userContent) {
		this.userContent = userContent;
	}
	
	@NotNull	
	@Size(min=1, max=128)
	@Column(name="full_name")	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@OneToOne
	@JoinColumn(name = "invite_id")
	public Invite getInvite() {
		return invite;
	}

	public void setInvite(Invite invite) {
		this.invite = invite;
	}

	//required by interface, returning email address instead
	@Transient
	public String getUsername() {
		return email;
	}
	
	//these booleans are required by UserDetails interface, using enabled state for all
	@Transient
	public boolean isAccountNonExpired() {
		return enabled;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return enabled;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return enabled;
	}

	@Transient	
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : getRoles()) {		
			authorities.add(new GrantedAuthorityImpl(role.getName()));
		}

		return authorities;
	}
	
	public void fullyEnable() {
		enabled = true;
	}
	
	public String toString() {
		return String.format("id = [%s], email = [%s], password = [%s], fullName = [%s]", this.id, this.email, this.password, this.fullName);
	}
	
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof User)) return false;
		
		final User user = (User)other;
		if (!user.getEmail().equals( getEmail())) return false;
		
		return true;
	}
	
	public int hashCode() {
		int result = HashCodeUtil.SEED;

		result = HashCodeUtil.hash(result, getEmail());
		
		return result;		
	}
	
	
}
