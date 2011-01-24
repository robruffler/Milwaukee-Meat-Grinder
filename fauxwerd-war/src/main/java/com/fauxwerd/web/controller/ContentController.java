package com.fauxwerd.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fauxwerd.model.Content;
import com.fauxwerd.model.ContentStatus;
import com.fauxwerd.model.Site;
import com.fauxwerd.model.User;
import com.fauxwerd.model.UserContent;
import com.fauxwerd.service.ContentService;
import com.fauxwerd.service.UserService;

@Controller
@RequestMapping(value="/content")
public class ContentController {
	
    final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired 
    private ContentService contentService; 
    @Autowired
    private UserService userService;
	
	@RequestMapping(value="/{contentIdString}", method=RequestMethod.GET)
	public ModelAndView getContent(@PathVariable("contentIdString") String contentIdString) {
		Long contentId = null;

		try {
			contentId = Long.valueOf(contentIdString);
		}
		catch (NumberFormatException e) {
			if(log.isErrorEnabled()) {
				log.error("", e);
			}
		}
		
		Content content = contentService.getContentById(contentId);
		ModelAndView modelAndView = new ModelAndView("content/view", "content", content);		
		return modelAndView;		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String addContent(HttpServletRequest req, HttpServletResponse res, Model model) {
				
		String urlString = req.getParameter("url");
		URL url = null;
		String userIdString = req.getParameter("userId");
		Long userId = null;
				
		try {
			urlString = URLDecoder.decode(urlString, "utf-8");
			
			//TODO improve this to work for twitter (e.g. http://twitter.com/#!/srcasm)
			//drop any url fragments (#)
			int fragmentIndex = urlString.indexOf('#');
			if (fragmentIndex > 0) {
				urlString = urlString.substring(0, fragmentIndex);
			}
			
			url = new URL(urlString);
			userId = Long.valueOf(userIdString);
		}
		catch (UnsupportedEncodingException e) {
			if (log.isErrorEnabled()) {
				log.error("", e);				
			}
		}
		catch (MalformedURLException e) {
			if (log.isErrorEnabled()) {
				log.error("", e);
			}
		}
		catch (NumberFormatException e) {
			if(log.isErrorEnabled()) {
				log.error("", e);
			}
		}
		
		if(log.isDebugEnabled()) {
			log.debug(String.format("url = %s, id = %d", url, userId));
		}

		//TODO move most of this code related to writing to a file to a service to clean up this controller
				
		String siteHostname = url.getAuthority();
		
		if (log.isDebugEnabled()) {
			log.debug(String.format("Site = %s", siteHostname));
		}
		
		//check if Site has already been saved
		Site site = null;
		Site existingSite = contentService.getSiteByHostname(siteHostname);
		
		if (existingSite != null) {
			site = existingSite;
		}
		else {
			site = new Site();
			site.setHostname(siteHostname);
			contentService.addSite(site);
		}		
		
		User user = userService.getUserById(userId);
		
		//check if this content has been saved before
		Content content = null;
		UserContent userContent = null;
		Content existingContent = contentService.getContentByUrl(url.toString());
		
		if (existingContent != null) {
			content = existingContent;
			
			//TODO check if the content has been updated at the source and update record accordingly
			//resetting status to saved will cause scheduled jobs to refetch content
			content.setStatus(ContentStatus.SAVED);
			contentService.updateContent(content);
			
			//check if this user has saved this content before
			if (log.isDebugEnabled()) {
				log.debug(String.format("Saving an existing piece of content, id = %s", content.getId()));
			}
						
			for (UserContent someUserContent : user.getUserContent()) {
				if (content.getUrl().equals(someUserContent.getContent().getUrl())) {
					userContent = someUserContent;
					if (log.isDebugEnabled()) log.debug(String.format("currently saved for this user: %s", someUserContent.getId()));
				}
			}
		}
		else {		
			if (log.isDebugEnabled()) {
				log.debug("adding new record to content table");
			}
			
			content = new Content();
			content.setUrl(url.toString());
			content.setSite(site);
			content.setStatus(ContentStatus.SAVED);
	
			contentService.addContent(content);
		}
		
		if (userContent == null) {
			if (log.isDebugEnabled()) {
				log.debug("adding new user content record");
			}			
			userContent = new UserContent(user, content);					
			contentService.addUserContent(userContent);
		}
		else {
			if (log.isDebugEnabled()) {
				log.debug("updating existing user content record");
			}
			//TODO update - this isn't actually updating anyting at the moment 			
			contentService.updateUserContent(userContent);
		}
		
		return "content/saved";
	}

}
