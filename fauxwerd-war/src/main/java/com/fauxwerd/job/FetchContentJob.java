package com.fauxwerd.job;

import java.util.List;
import java.util.Map;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ServletContextAware;

import com.fauxwerd.model.Content;
import com.fauxwerd.model.ContentStatus;
import com.fauxwerd.service.ContentService;
import com.fauxwerd.service.ContentServiceImpl;

@Component
public class FetchContentJob implements ServletContextAware {
	
	final Logger log = LoggerFactory.getLogger(getClass());
	
    @Autowired 
    private ContentService contentService; 
    
    private ServletContext servletContext;
    
    @Resource(name = "site")
    private Map<String, String> siteProperties;
    		
	public void fetch() {				
		List<Content> savedContent = contentService.listSavedContent();
		
		for (Content content : savedContent) {;
			if (log.isDebugEnabled()) {
				log.debug(String.format("fetching %s", content.getUrl()));
			}
			
			RestTemplate restTemplate = new RestTemplate();
			
			String rawHtml = restTemplate.getForObject(content.getUrl(), String.class);
			
//			if (log.isDebugEnabled()) {
//				log.debug("--------------------------------------------");
//				log.debug(rawHtml);
//				log.debug("--------------------------------------------");
//			}
			
			String dataDirectoryPath = siteProperties.get("dataStore");			

			String siteDirectoryPath = content.getSite().getHostname();
			siteDirectoryPath = siteDirectoryPath.replace('.', '_');
			
			try {
				//check if data directory exists
				File dataDirectory = new File(dataDirectoryPath);
				
				if (!dataDirectory.exists()) {
					dataDirectory.mkdir();
				}
				
				//check if site directory exists
				File siteDirectory = new File(dataDirectory, siteDirectoryPath);
				
				if (!siteDirectory.exists()) {
					siteDirectory.mkdir();
				}				
				
				File file = new File(siteDirectory, content.getId().toString() + "-raw.html");
								
				if (!file.exists()) {
					file.createNewFile();
				}
								
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(rawHtml);
				bw.close();

				content.setRawHtmlPath(file.getAbsolutePath());
				content.setStatus(ContentStatus.FETCHED);
				contentService.updateContent(content);
				
				if (log.isDebugEnabled()) {
					log.debug(String.format("saved to %s", file.getAbsolutePath()));
				}
			}
			catch (IOException e) {
				if (log.isErrorEnabled()) {
					log.error("", e);
				}
			}
		}
				
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
