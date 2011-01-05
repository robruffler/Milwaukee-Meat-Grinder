package com.fauxwerd.job;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.fauxwerd.service.ContentService;

@Component
public class FetchContentJob implements ServletContextAware {
	
	final Logger log = LoggerFactory.getLogger(getClass());
	
    @Autowired 
    private ContentService contentService; 
    
    private ServletContext servletContext;
        		
	public void fetch() {
		
		contentService.fetchContent();
						
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
