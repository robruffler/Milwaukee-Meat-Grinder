package com.fauxwerd.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fauxwerd.dao.ContentDAO;
import com.fauxwerd.model.Content;
import com.fauxwerd.model.ContentStatus;
import com.fauxwerd.model.Site;
import com.fauxwerd.model.UserContent;

@Service("contentService")
public class ContentServiceImpl implements ContentService {
	
	final Logger log = LoggerFactory.getLogger(getClass());
	
    @Resource(name = "site")
    private Map<String, String> siteProperties;
	
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
    public List<Content> listContentByStatus(ContentStatus status) {
    	return contentDAO.listContentByStatus(status);
    }
    
    @Transactional
    public List<Content> fetchContent() {

		List<Content> savedContent = listContentByStatus(ContentStatus.SAVED);
		
		for (Content content : savedContent) {
			
			if (log.isDebugEnabled()) {
				log.debug(String.format("fetching %s", content.getUrl()));
			}
			
			RestTemplate restTemplate = new RestTemplate();

			String rawHtml = null;
			
			try {
				rawHtml = restTemplate.getForObject(content.getUrl(), String.class);
				
				String dataDirectoryPath = siteProperties.get("dataStore");			

				String siteDirectoryPath = content.getSite().getHostname();
				siteDirectoryPath = siteDirectoryPath.replace('.', '_');
				
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
				updateContent(content);
				
				if (log.isDebugEnabled()) {
					log.debug(String.format("saved to %s", file.getAbsolutePath()));
				}
				
			}
			catch (RestClientException e) {
				if (log.isErrorEnabled()) {
					log.error("", e);
				}
				
				//set content status to FETCH_ERROR				
				content.setStatus(ContentStatus.FETCH_ERROR);
				updateContent(content);				
			}
			catch (IOException e) {
				if (log.isErrorEnabled()) {
					log.error("", e);
				}
			}
					
		}
    	    	
    	return savedContent;
    }
    
    @Transactional
    public List<Content> parseContent() {
		List<Content> fetchedContent = listContentByStatus(ContentStatus.FETCHED);

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine pyEngine = mgr.getEngineByName("python");

		for (Content content : fetchedContent) {		

			if (log.isDebugEnabled()) {
				log.debug(String.format("parsing %s", content.getRawHtmlPath()));
			}
			
	        try {
//TODO implement actual html parsing	        	
	        	pyEngine.eval("print \"Python - Hello, world!\"");
	        	
				content.setStatus(ContentStatus.PARSED);
				updateContent(content);
				
				if (log.isDebugEnabled()) {
					log.debug(String.format("parsed to %s", "parsed file path goes here"));
				}
				
	        }
	        catch (Exception e) {
	        	if (log.isErrorEnabled()) {
	        		log.error("", e);
	        	}
				content.setStatus(ContentStatus.PARSE_ERROR);
				updateContent(content);
	        }
	        	        
		}
    	
		return fetchedContent;
    }
}


 
