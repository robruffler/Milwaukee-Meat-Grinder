package com.fauxwerd.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.fauxwerd.service.ContentService;

@Component
public class ParseContentJob {
	
	final Logger log = LoggerFactory.getLogger(getClass());
	
    @Autowired 
    private ContentService contentService; 	

	public void parse() {
		if (log.isDebugEnabled()) {
			log.debug("parsing ahoy!");
		}
		
		contentService.parseContent();
		
	}
}
