package com.fauxwerd.service;


import org.springframework.security.core.userdetails.UserDetails;

import com.fauxwerd.model.User;

public interface UserService {

	String createUser(User user);
	
	User getUser(String username);
	
	UserDetails loadUserByUsername(String arg0);
	
}
