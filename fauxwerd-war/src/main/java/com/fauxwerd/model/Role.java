package com.fauxwerd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

@Entity
@Table(name = "role")
public class Role {

	private Long id;
	private Integer version;
	private String name;
	private List<User> users = new ArrayList<User>();
		
	public Role() {}
	
	public Role(String name) {
		this.name = name;
	}

	@Id 
	@GeneratedValue (generator = "ROLE_TABLE_GEN")
	@TableGenerator (
			name = "ROLE_TABLE_GEN",
			pkColumnValue = "ROLE",
			allocationSize = 2
	)
	@Column(name = "role_id")	
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

	@Column(columnDefinition = "varchar(50)")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(mappedBy = "roles")	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
