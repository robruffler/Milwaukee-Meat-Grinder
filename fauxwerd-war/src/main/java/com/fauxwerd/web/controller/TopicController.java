package com.fauxwerd.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fauxwerd.model.Topic;
import com.fauxwerd.service.TopicService;

@Controller
public class TopicController {
	
	private static final Logger log = LoggerFactory.getLogger(TopicController.class);
	
	@Autowired
	private TopicService topicService;
	
	@RequestMapping(value = "/topic/add", method = RequestMethod.GET)
	public ModelAndView prepareAddTopic(HttpServletRequest req, HttpServletResponse res) {
		
		if(log.isDebugEnabled()) log.debug("in addTopic() GET");
		
		Topic topic = new Topic();
		
		return new ModelAndView("topic/add", "topic", topic);
	}
	
	@RequestMapping(value = "/topic/add", method = RequestMethod.POST)
	public @ResponseBody Topic addTopic(HttpServletRequest req, HttpServletResponse res) {
		if(log.isDebugEnabled()) log.debug("in addTopic() POST");

		String name = req.getParameter("topic");
		
		if (log.isDebugEnabled()) log.debug(String.format("name = %s", name));
			
		Topic topic = new Topic(name);
		
		topicService.saveOrUpdateTopic(topic);
		
		//TODO need to figure out how to handle requests that are called by ajax
		
		return topic;
	}
	
	@RequestMapping(value = "/topic/list")
	public ModelAndView listTopics() {
		
		if(log.isDebugEnabled()) log.debug("in listTopics()");
		
		List<Topic> topics = topicService.listTopics();
		
		return new ModelAndView("topic/list", "topics", topics);
	}
	
	@RequestMapping(value = "topic/{topicIdString}")
	public ModelAndView viewTopic(@PathVariable("topicIdString") String topicIdString) {
		if (log.isDebugEnabled()) log.debug("in viewTopic()");
		
		Long topicId = null;
		
		try {
			topicId = Long.valueOf(topicIdString);
		}
		catch (NumberFormatException e) {
			if(log.isErrorEnabled()) {
				log.error("", e);
			}
		}
				
		Topic topic = topicService.getTopic(topicId);
		
		return new ModelAndView("topic/view", "topic", topic);
	}
	

}
