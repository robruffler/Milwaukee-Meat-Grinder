package com.fauxwerd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fauxwerd.dao.ContentDAO;
import com.fauxwerd.model.Content;
import com.fauxwerd.model.Site;
import com.fauxwerd.model.UserContent;

@Service("contentService")
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentDAO contentDAO;
 
    @Transactional
    public void addContent(Content content) {
        contentDAO.addContent(content);
    }
    
    @Transactional
	public void addSite(Site site) {
    	contentDAO.addSite(site);
    }
    
    @Transactional
    public void addUserContent(UserContent userContent) {
    	contentDAO.addUserContent(userContent);
    }
    
    @Transactional
    public void updateContent(Content content) {
    	contentDAO.updateContent(content);
    }
    
    @Transactional
    public void updateUserContent(UserContent userContent) {
    	contentDAO.updateUserContent(userContent);
    }
    
    @Transactional
    public Content getContentById(Long id) {
    	return contentDAO.getContentById(id);
    }
    
    @Transactional
    public Content getContentByUrl(String url) {
    	return contentDAO.getContentByUrl(url);
    }
    
    @Transactional
    public Site getSiteByHostname(String hostname) {
    	return contentDAO.getSiteByHostname(hostname);
    }
 
    @Transactional
    public List<Content> listContent() { 
        return contentDAO.listContent();
    }

    @Transactional
    public List<Content> listSavedContent() { 
        return contentDAO.listSavedContent();
    }
        
}


 
