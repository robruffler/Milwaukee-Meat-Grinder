package com.fauxwerd.dao;

import java.util.List;

import com.fauxwerd.model.Topic;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TopicDAOImpl implements TopicDAO {

    @Autowired
    private SessionFactory sessionFactory;
		
	@Override
	public void saveOrUpdateTopic(Topic topic) {
		sessionFactory.getCurrentSession().saveOrUpdate(topic);
	}
	
	@SuppressWarnings("unchecked")
	public List<Topic> listTopics() {
		return sessionFactory.getCurrentSession().createQuery("from Topic order by name").list();
	}
	
	public Topic getTopicByName(String name) {
		return (Topic)sessionFactory.getCurrentSession().createQuery("from Topic where name = ?")
			.setString(0, name)
			.uniqueResult();
	}
	
	public Topic getTopic(Long id) {
		return (Topic)sessionFactory.getCurrentSession().load(Topic.class, id);
	}

}
