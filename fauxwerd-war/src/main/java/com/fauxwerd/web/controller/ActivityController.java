package com.fauxwerd.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fauxwerd.model.Activity;
import com.fauxwerd.model.User;
import com.fauxwerd.service.ActivityService;
import com.fauxwerd.service.UserService;

@Controller
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/activity-all")
	public ModelAndView allActivityFeed(HttpServletRequest req, HttpServletResponse res) {
		List<Activity> activityFeed = activityService.listAllActivity();
		
		return new ModelAndView("activity/feed", "activityFeed", activityFeed);
	}

	@RequestMapping(value = "/activity")
	public ModelAndView activityFeed(HttpServletRequest req, HttpServletResponse res) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		user = userService.getUserById(user.getId());
		
		List<Activity> activityFeed = activityService.listActivityOfUsersFollowedByUser(user);
		
		return new ModelAndView("activity/feed", "activityFeed", activityFeed);
	}
	
	
}
