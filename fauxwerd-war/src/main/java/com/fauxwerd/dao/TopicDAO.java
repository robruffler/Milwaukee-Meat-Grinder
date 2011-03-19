package com.fauxwerd.dao;

import java.util.List;

import com.fauxwerd.model.Topic;

public interface TopicDAO {

	public void saveOrUpdateTopic(Topic topic);
	
	public List<Topic> listTopics();
	
	public Topic getTopicByName(String name);
	
	public Topic getTopic(Long id);
	
}
