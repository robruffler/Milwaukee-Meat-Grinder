package com.fauxwerd.web.tag;

import java.io.File;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fauxwerd.model.Content;
import com.fauxwerd.service.ContentService;

public class ContentTag extends TagSupport {
	
	private static final long serialVersionUID = 4908571400700017733L;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private WebApplicationContext applicationContext;
	private ContentService contentService;

	private String contentId = null;
	
	
	public int doStartTag() throws JspException {
		
		applicationContext = RequestContextUtils.getWebApplicationContext(
				pageContext.getRequest(),
				pageContext.getServletContext());
		
		contentService = (ContentService)applicationContext.getBean("contentService");
				
		try {
			Long id = Long.valueOf(getContentId());
			
			Content content = contentService.getContentById(id);
						
			String parsedHtml = FileUtils.readFileToString(new File(content.getParsedHtmlPath()));
						
			pageContext.getOut().print(parsedHtml);
		} catch (Exception e) {
			throw new JspTagException("ContentTag: " + e.getMessage());
		}
		return SKIP_BODY;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}	
	
}
