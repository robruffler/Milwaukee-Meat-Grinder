package com.fauxwerd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fauxwerd.dao.ContentDAO;
import com.fauxwerd.model.Content;

@Service
public class ContentService {
    @Autowired
    private ContentDAO contentDAO;
 
    @Transactional
    public void addContent(Content content) {
        contentDAO.addContent(content);
    }
 
    @Transactional
    public List<Content> listContent() { 
        return contentDAO.listContent();
    }
/*
    @Transactional
    public void removeContent(Integer id) {
        contentDAO.removeContent(id);
    }
*/    
}


 
