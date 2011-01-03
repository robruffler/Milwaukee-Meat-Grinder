package com.fauxwerd.service;

import java.util.List;

import com.fauxwerd.model.Content;
import com.fauxwerd.model.Site;
import com.fauxwerd.model.UserContent;

public interface ContentService {

	public void addContent(Content content);
	
	public void addSite(Site site);
	
	public void addUserContent(UserContent content);
	
	public void updateContent(Content content);
	
	public void updateUserContent(UserContent userContent);
	
	public Content getContentById(Long id);
	
	public Content getContentByUrl(String url);
	
	public Site getSiteByHostname(String url);
	
	public List<Content> listContent();
	
	public List<Content> listSavedContent();
}
