package com.fauxwerd.dao;

import java.util.List;

import com.fauxwerd.model.Invite;
import com.fauxwerd.model.PasswordResetRequest;
import com.fauxwerd.model.Role;
import com.fauxwerd.model.User;
import com.fauxwerd.model.UserFollow;

public interface UserDAO {

	public void saveUser(User entity);
	
	public void saveOrUpdateUser(User user);
		
	public User getUser(String email);
	
	public User getUserById(Long userId);
	
	public Role getRole(String name);
	
	public List<User> listUsers();
	
	public List<User> listUsersExcept(User user);
	
	public void addInvite(Invite invite);
	
	public void updateInvite(Invite invite);
	
	public Invite getInvite(String code);
	
	public void addPasswordResetRequest(PasswordResetRequest request);
	
	public PasswordResetRequest getPasswordResetRequest(String code);
	
	public void saveOrUpdateUserFollow(UserFollow userFollow);
	
	public void followUser(User user, User toFollow);
	
	public void unfollowUser(User user, User toFollow);
}
