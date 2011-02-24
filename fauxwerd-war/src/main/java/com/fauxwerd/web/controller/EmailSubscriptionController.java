package com.fauxwerd.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fauxwerd.model.EmailSubscription;

@Controller
public class EmailSubscriptionController {

	public static final Logger log = LoggerFactory.getLogger(EmailSubscriptionController.class);
	public static final String API_KEY = "f0df9e136fc0a0750ac9200a075293f8-us2";
	public static final String API_ENDPOINT = "http://us2.api.mailchimp.com/1.3/";
	public static final String API_SUBSCRIBE = "?method=listSubscribe";
	
	@RequestMapping (value="/subscribe")
	public ModelAndView subscribe(@Valid EmailSubscription emailSubscription, BindingResult result, HttpServletRequest req, HttpServletResponse res) {

		if (result.hasErrors()) {
			return new ModelAndView("index", "emailSubscription", emailSubscription);
		}
						
		RestTemplate restTemplate = new RestTemplate();
			
		List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
		list.add(new MappingJacksonHttpMessageConverter());
		restTemplate.setMessageConverters(list);
				
		emailSubscription.setApiKey(API_KEY);
		
		ResponseEntity<String> entity = restTemplate.postForEntity(API_ENDPOINT + API_SUBSCRIBE, emailSubscription, String.class);		
				
		String body = entity.getBody();
		MediaType contentType = entity.getHeaders().getContentType();
		HttpStatus statusCode = entity.getStatusCode();
		
		if (log.isDebugEnabled()) {
			log.debug(String.format("body = %s", body));
			log.debug(String.format("contentType = %s", contentType));
			log.debug(String.format("statusCode = %s", statusCode));
		}
				
		//set cookie so we can change homepage view
		res.addCookie(new Cookie("subscribed","true"));
		
		return new ModelAndView("redirect:/");
	}
}
