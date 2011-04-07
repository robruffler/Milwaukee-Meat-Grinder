package com.fauxwerd.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

public interface MailService {
	
	public void sendMail(String email, String subject, String body) throws MessagingException, UnsupportedEncodingException;

}
