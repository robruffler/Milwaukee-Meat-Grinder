package com.fauxwerd.dao;

import java.util.List;

import com.fauxwerd.model.User;

public interface UserDAO {

	public void saveUser(User entity);
	
	public User getUser(String email);
	
	public User getUserById(Long userId);
	
	public List<User> listUsers();	
}
