package com.fauxwerd.service;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.fauxwerd.model.User;

public interface UserService {

	public String createUser(User user);
	
	public User getUser(String username);
	
	public User getUserById(Long userId);
	
	public UserDetails loadUserByUsername(String arg0);
	
	public List<User> listUsers();
	
}
