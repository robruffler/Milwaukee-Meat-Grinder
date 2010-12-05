package com.fauxwerd.dao;

import java.util.List;

import com.fauxwerd.model.Content;

public interface ContentDAO {

	public void addContent(Content content);
	
	public List<Content> listContent();
}
