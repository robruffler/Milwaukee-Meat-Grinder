package com.fauxwerd.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fauxwerd.model.EmailSubscription;
import com.fauxwerd.model.User;

@Controller
public class HomepageController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/")
	public ModelAndView loadHomepage(HttpServletRequest req, HttpServletResponse res) {
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (log.isDebugEnabled()) log.debug(String.format("user = %s", user));
		
		//send to profile page if user is logged in (anonymousUser is just a String)
		if (user instanceof User) return new ModelAndView("redirect:/user/profile");

		//otherwise load homepage
		EmailSubscription emailSubscription = new EmailSubscription();
    	return new ModelAndView("index", "emailSubscription", emailSubscription);
		
	}

}
