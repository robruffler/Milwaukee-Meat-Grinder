package com.fauxwerd.web.controller;

import com.fauxwerd.model.User;
import com.fauxwerd.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/save")
public class SaveController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private UserService userService;

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getIform(HttpServletRequest req, HttpServletResponse res, Model model) {
		ModelAndView modelAndView = new ModelAndView("save");
		String url = req.getParameter("url");
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			url = URLDecoder.decode(url, "utf-8");
		} 
		catch (UnsupportedEncodingException e) {
			if (log.isErrorEnabled()) {
				log.error("", e);				
			}
		}
		//this is necessary b/c of hibernate lazy loading weirdness
		user = userService.getUserById(user.getId());
		
		modelAndView.addObject("user", user);
		model.addAttribute("url", url);
		
		return modelAndView;
	}
}