package com.fauxwerd.dao;

import java.util.List;

import com.fauxwerd.model.Content;
import com.fauxwerd.model.ContentStatus;
import com.fauxwerd.model.Site;
import com.fauxwerd.model.Topic;
import com.fauxwerd.model.UserContent;

public interface ContentDAO {

	public void addContent(Content content);
	
	public void addTopicToContent(Content content, Topic topic);
	
	public void removeTopicFromContent(Long contentId, Long topicId);
	
	public void addSite(Site site);
	
	public void addUserContent(UserContent userContent);
	
	public void updateContent(Content content);
	
	public void updateUserContent(UserContent userContent);
	
	public Content getContentById(Long id);
	
	public Content getContentByUrl(String url);
	
	public Site getSiteByHostname(String hostname);
	
	public List<Content> listContent();
		
	public List<Content> listContentByStatus(ContentStatus status);
	
}
