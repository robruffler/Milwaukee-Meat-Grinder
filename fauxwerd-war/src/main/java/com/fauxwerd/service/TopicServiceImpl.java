package com.fauxwerd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fauxwerd.dao.TopicDAO;
import com.fauxwerd.model.Topic;

@Service("topicService")
public class TopicServiceImpl implements TopicService {

	@Autowired
	private TopicDAO topicDAO;
	
	@Transactional
	public void saveOrUpdateTopic(Topic topic) {
		topicDAO.saveOrUpdateTopic(topic);
	}
	
	@Transactional
	public List<Topic> listTopics() {
		return topicDAO.listTopics();
	}
	
	@Transactional
	public Topic getTopicByName(String name) {
		return topicDAO.getTopicByName(name);
	}
	
	@Transactional
	public Topic getTopic(Long id) {
		return topicDAO.getTopic(id);
	}

}
