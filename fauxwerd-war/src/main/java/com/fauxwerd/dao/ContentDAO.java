package com.fauxwerd.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fauxwerd.model.Content;

@Repository
public class ContentDAO {

    @Autowired
    private SessionFactory sessionFactory;
 
    public void addContent(Content content) {
        sessionFactory.getCurrentSession().save(content);
    }
 
    public List<Content> listContent() {
 
        return sessionFactory.getCurrentSession().createQuery("from Content")
                .list();
    }
 
    public void removeContent(Integer id) {
        Content content = (Content) sessionFactory.getCurrentSession().load(
                Content.class, id);
        if (null != content) {
            sessionFactory.getCurrentSession().delete(content);
        }
 
    }
	
}