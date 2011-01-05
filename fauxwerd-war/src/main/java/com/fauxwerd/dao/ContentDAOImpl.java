package com.fauxwerd.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fauxwerd.model.Content;
import com.fauxwerd.model.ContentStatus;
import com.fauxwerd.model.Site;
import com.fauxwerd.model.UserContent;

@Repository
public class ContentDAOImpl implements ContentDAO {

    @Autowired
    private SessionFactory sessionFactory;
 
    public void addContent(Content content) {
        sessionFactory.getCurrentSession().save(content);
    }
    
	public void addSite(Site site) {
		sessionFactory.getCurrentSession().save(site);
	}
    
    public void addUserContent(UserContent userContent) {
    	sessionFactory.getCurrentSession().save(userContent);
    }

    public void updateContent(Content content) {
        sessionFactory.getCurrentSession().update(content);
    }

    public void updateUserContent(UserContent userContent) {
    	sessionFactory.getCurrentSession().update(userContent);
    }
    
    public Content getContentById(Long id) {
    	return (Content)sessionFactory.getCurrentSession().get(Content.class, id);    	
    }
    
    public Content getContentByUrl(String url) {    	
    	return (Content)sessionFactory.getCurrentSession().createQuery("from Content as content where content.url = ?")
    		.setString(0, url)
    		.uniqueResult();
    }
    
    public Site getSiteByHostname(String hostname) {
    	return (Site)sessionFactory.getCurrentSession().createQuery("from Site as site where site.hostname = ?")
    		.setString(0, hostname)
    		.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public List<Content> listContent() { 
        return sessionFactory.getCurrentSession().createQuery("from Content")
                .list();
    }

    @SuppressWarnings("unchecked")
    public List<Content> listContentByStatus(ContentStatus status) {
        return sessionFactory.getCurrentSession().createQuery("from Content as content where content.status = ?")
		.setParameter(0, status)
        .list();    	
    }
        	
}