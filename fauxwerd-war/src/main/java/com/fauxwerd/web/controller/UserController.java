package com.fauxwerd.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import javax.validation.Valid;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import com.fauxwerd.model.User;
import com.fauxwerd.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private UserService userService;
    
	//@Resource - need to understand best way to annotate dependencies when multiple options exist
	@Autowired
	@Qualifier("org.springframework.security.authenticationManager")
	private ProviderManager authenticationManager;
        
    @RequestMapping(value = "/register")
    public ModelAndView register() {
    	User user = new User();
    	return new ModelAndView("user/register", "user", user);
    }
    
	@RequestMapping(value = "register", method = RequestMethod.POST)	
	public ModelAndView create(@Valid User user, BindingResult result, HttpServletRequest req, HttpServletResponse res) {
		if (result.hasErrors()) {
			return new ModelAndView("user/register", "user", user);
		}
		
		if (log.isDebugEnabled()) {
			log.debug(String.format("User = %s", user));
		}
		
		String unhashedPassword = user.getPassword(); 
		
		userService.createUser(user);
		
		if (log.isDebugEnabled()) {
			log.debug(String.format("Created user [%s]", user));
		}
				
		autoLogin(req, res, user.getEmail(), unhashedPassword);
						
		return new ModelAndView("redirect:/user/profile", "user", user);
	}
	
	@RequestMapping(value = "/profile")
	public ModelAndView profile() {
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//this is necessary b/c of hibernate lazy loading weirdness
		user = userService.getUserById(user.getId());
		
		return new ModelAndView("user/profile", "user", user);
	}
	
	@RequestMapping(value = "wrong-bookmark", method = RequestMethod.GET)
	public ModelAndView wrongBookmark(HttpServletRequest req, HttpServletResponse res, Model model) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user = userService.getUserById(user.getId());
		
		String url = req.getParameter("url");
		
		try {
			url = URLDecoder.decode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			if (log.isErrorEnabled()) {
				log.error("", e);
			}
		}
		
		model.addAttribute("url", url);
		return new ModelAndView("user/wrong-bookmark","user", user);
	}
    
	//currently called after registration to automatically log user in
	public void autoLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		try {
	      // Must be called from request filtered by Spring Security, otherwise SecurityContextHolder is not updated
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
			token.setDetails(new WebAuthenticationDetails(request));
			Authentication authentication = authenticationManager.authenticate(token);
			
			if (log.isDebugEnabled()) {
				log.debug(String.format("Logging in with %s", authentication.getPrincipal()));
			}
			SecurityContextHolder.getContext().setAuthentication(authentication);
	    } 
		catch (Exception e) {
			SecurityContextHolder.getContext().setAuthentication(null);
			if (log.isErrorEnabled()) {
				log.error("Failure in autoLogin", e);
			}
	    }
	}	
	
}
