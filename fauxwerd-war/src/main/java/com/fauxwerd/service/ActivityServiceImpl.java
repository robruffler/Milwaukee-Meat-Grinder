package com.fauxwerd.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fauxwerd.dao.ActivityDAO;
import com.fauxwerd.model.Activity;
import com.fauxwerd.model.User;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
	
	private static final Logger log = LoggerFactory.getLogger(ActivityService.class);
	
	@Autowired
	private ActivityDAO activityDAO;

	@Transactional
	public void saveOrUpdateActivity(Activity activity) {				
		activityDAO.saveOrUpdateActivity(activity);
	}
	
	@Transactional
	public List<Activity> listAllActivity() {
		return activityDAO.listAllActivity();
	}
	
	@Transactional
	public List<Activity> listActivityOfUsersFollowedByUser(User user) {
		return activityDAO.listActivityOfUsersFollowedByUser(user);
	}

}
