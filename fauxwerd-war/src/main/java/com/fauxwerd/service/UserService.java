package com.fauxwerd.service;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.fauxwerd.model.Invite;
import com.fauxwerd.model.Role;
import com.fauxwerd.model.User;

public interface UserService {

	public String createUser(User user);
	
	public User getUser(String username);
	
	public User getUserById(Long userId);
	
	public Role getRole(String name);
	
	public UserDetails loadUserByUsername(String arg0);
	
	public List<User> listUsers();
	
	public List<User> listUsersExcept(User user);
	
	public void addInvite(Invite invite);
	
	public void updateInvite(Invite invite);
	
	public Invite getInvite(String code);
	
	public void followUser(User user, User toFollow);
	
	public void unfollowUser(User user, User toUnfollow);
}
