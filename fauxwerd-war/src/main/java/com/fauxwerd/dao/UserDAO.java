package com.fauxwerd.dao;

import com.fauxwerd.model.User;

public interface UserDAO {

	void saveUser(User entity);
	
	User getUser(String email);
	
	User getUserById(Long userId);
}
