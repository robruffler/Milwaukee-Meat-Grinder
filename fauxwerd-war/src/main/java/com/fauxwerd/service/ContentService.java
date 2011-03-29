package com.fauxwerd.service;

import java.net.URL;
import java.util.List;

import com.fauxwerd.model.Content;
import com.fauxwerd.model.ContentStatus;
import com.fauxwerd.model.Site;
import com.fauxwerd.model.Topic;
import com.fauxwerd.model.User;
import com.fauxwerd.model.UserContent;

public interface ContentService {

	public void addContent(Content content);
	
	public Content addContent(URL url, User user, String title);
	
	public Content addContent(URL url, User user, String title, String page);
	
	public void addTopicToContent(Content content, Topic topic);
	
	public void removeTopicFromContent(Long contentId, Long topicId);	
	
	public void addSite(Site site);
	
	public void addUserContent(UserContent content);
	
	public void updateContent(Content content);
	
	public void updateUserContent(UserContent userContent);
	
	public Content getContentById(Long id);
	
	public Content getContentByUrl(String url);
	
	public Site getSiteByHostname(String url);
	
	public List<Content> listContent();
	
	public List<Content> listContentByStatus(ContentStatus status);	
	
	public List<Content> fetchContent();
	
	public List<Content> parseContent();
	
}
