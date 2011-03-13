package com.fauxwerd.service;

import java.util.List;

import com.fauxwerd.model.Activity;
import com.fauxwerd.model.User;

public interface ActivityService {

	public void saveOrUpdateActivity(Activity activity);
	
	public List<Activity> listAllActivity();
	
	public List<Activity> listActivityOfUsersFollowedByUser(User user);
	
}
