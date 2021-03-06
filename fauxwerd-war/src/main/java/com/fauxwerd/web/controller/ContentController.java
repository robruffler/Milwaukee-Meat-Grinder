package com.fauxwerd.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fauxwerd.model.Activity;
import com.fauxwerd.model.ActivityType;
import com.fauxwerd.model.Content;
import com.fauxwerd.model.Topic;
import com.fauxwerd.model.User;
import com.fauxwerd.service.ActivityService;
import com.fauxwerd.service.ContentService;
import com.fauxwerd.service.TopicService;
import com.fauxwerd.service.UserService;

@Controller
@RequestMapping(value="/content")
public class ContentController {
	
    final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired 
    private ContentService contentService; 
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TopicService topicService;
	
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
		String title = req.getParameter("title");
		String page = req.getParameter("page");
			
		if (log.isDebugEnabled()) log.debug(String.format("title = %s", title));
		if (log.isDebugEnabled()) log.debug(String.format("page = %s", page));
		if (log.isDebugEnabled()) log.debug(String.format("urlString = %s", urlString));
				
		try {
			urlString = URLDecoder.decode(urlString, "utf-8");
			title = URLDecoder.decode(title, "utf-8");
			page = URLDecoder.decode(page, "utf-8");
			
			if (log.isDebugEnabled()) log.debug(String.format("title after decode = %s", title));
			if (log.isDebugEnabled()) log.debug(String.format("page after decode = %s", page));
			if (log.isDebugEnabled()) log.debug(String.format("urlString after decode = %s", urlString));
			
			//drop any url fragments (#) that aren't ajax crawlable			
			if (!(urlString.indexOf("#!") > 0) && urlString.indexOf('#') > 0) {			
				urlString = urlString.substring(0, urlString.indexOf('#'));
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
			log.debug(String.format("url = %s, id = %d, title = %s", url, userId, title));
		}
		
		User user = userService.getUserById(userId);
		
		Content content = contentService.addContent(url, user, title, page);
		
		Activity activity = new Activity(user, ActivityType.SAVED, content);
		
		if (log.isDebugEnabled()) log.debug(String.format("activity.getContent().getId() = %s", activity.getContent().getId()));
		
		activityService.saveOrUpdateActivity(activity);
						
		return "content/saved";
	}
	
	@RequestMapping(value = "/add-topic/{contentIdString}")
	public @ResponseBody Topic addTopicToContent(@PathVariable("contentIdString") String contentIdString, HttpServletRequest req, HttpServletResponse res) {
		Long contentId = null;
		String name = req.getParameter("topic");
		
		try {
			contentId = Long.valueOf(contentIdString);
		}
		catch (NumberFormatException e) {
			if(log.isErrorEnabled()) {
				log.error("", e);
			}
		}
		
		Content content = contentService.getContentById(contentId);
				
		Topic topic = new Topic(name);
		
		contentService.addTopicToContent(content, topic);
		
		return topic;		
	}
	
	@RequestMapping(value = "/remove-topic/{contentIdString}/{topicIdString}")
	public @ResponseBody Topic addTopicToContent(@PathVariable("contentIdString") String contentIdString, @PathVariable("topicIdString") String topicIdString,
			HttpServletRequest req, HttpServletResponse res) {
		Long contentId = null;
		Long topicId = null;
		
		try {
			contentId = Long.valueOf(contentIdString);
			topicId = Long.valueOf(topicIdString);
		}
		catch (NumberFormatException e) {
			if(log.isErrorEnabled()) {
				log.error("", e);
			}
		}
		
		if (log.isDebugEnabled()) log.debug(String.format("removing topicId [%s] from contentId [%s]", topicId, contentId));
		
		contentService.removeTopicFromContent(contentId, topicId);
		
		Topic topic = new Topic();
		return topic;
	}
	
}
