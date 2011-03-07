package com.fauxwerd.dao;

import java.util.List;

import com.fauxwerd.model.Invite;
import com.fauxwerd.model.Role;
import com.fauxwerd.model.User;

public interface UserDAO {

	public void saveUser(User entity);
	
	public User getUser(String email);
	
	public User getUserById(Long userId);
	
	public Role getRole(String name);
	
	public List<User> listUsers();	
	
	public void addInvite(Invite invite);
	
	public void updateInvite(Invite invite);
	
	public Invite getInvite(String code);
}
