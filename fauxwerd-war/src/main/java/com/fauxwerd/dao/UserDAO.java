package com.fauxwerd.dao;

import com.fauxwerd.model.User;

public interface UserDAO {

	void saveUser(User entity);

	//TODO remove commented out methods
	//User getUser(String username);
	
	User getUser(String email);
}
