package com.fauxwerd.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fauxwerd.model.Content;
import com.fauxwerd.model.User;
import com.fauxwerd.service.ContentService;
import com.fauxwerd.service.UserService;

@Controller
@RequestMapping(value="/content")
public class ContentController {
	
    final Log log = LogFactory.getLog(getClass());
    
    @Autowired 
    private ContentService contentService; 
    @Autowired
    private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getContent(Model model) {
		List content = contentService.listContent();
		ModelAndView modelAndView = new ModelAndView("content/list", "content", content);		
		return modelAndView;		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String addContent(HttpServletRequest req, HttpServletResponse res, Model model) {
				
		String url = req.getParameter("url");
		String userIdString = req.getParameter("userId");
		Long userId = null;
				
		try {
			url = URLDecoder.decode(url, "utf-8");
			userId = Long.valueOf(userIdString);
		}
		catch (UnsupportedEncodingException e) {
			if (log.isErrorEnabled()) {
				log.error(e);
			}
		}
		catch (NumberFormatException e) {
			if(log.isErrorEnabled()) {
				log.error(e);
			}
		}
		
		if(log.isDebugEnabled()) {
			log.debug(String.format("url = %s, id = %d", url, userId));
		}		
		
		User user = userService.getUserById(userId);
		
		Content content = new Content();
		content.setUrl(url);
		content.setUser(user);
		
		contentService.addContent(content);
		
		return "content/saved";
	}

}
