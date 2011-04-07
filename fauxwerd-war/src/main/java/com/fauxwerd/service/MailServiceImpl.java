package com.fauxwerd.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

@Service("mailService")
public class MailServiceImpl implements MailService {

    private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
    private static final String SMTP_AUTH_USER = "rob@fauxwerd.com";
    private static final String SMTP_AUTH_PWD  = "m3atgrind3r";	
	
	@Override
	public void sendMail(String email, String subject, String body) throws MessagingException, UnsupportedEncodingException {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
//        props.put("mail.mime.charset", "utf-8");
 
        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        // uncomment for debugging infos to stdout
        // mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();
 
        MimeMessage message = new MimeMessage(mailSession);
        
        message.setSubject(subject, "utf-8");
 
        Multipart multipart = new MimeMultipart();
 
        MimeBodyPart part1 = new MimeBodyPart();
        part1.setText(body, "utf-8", "html");
 
//        BodyPart part2 = new MimeBodyPart();
//        part2.setContent("<b>This is multipart mail and u read part2......</b>", "text/html");
 
        multipart.addBodyPart(part1);
//        multipart.addBodyPart(part2);
 
        message.setContent(multipart);
 
        message.setFrom(new InternetAddress("noreply@fauxwerd.com", "Fauxwerd"));
        message.addRecipient(Message.RecipientType.TO,
             new InternetAddress(email));
 
        transport.connect();
        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        transport.close();

	}
	
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = SMTP_AUTH_USER;
           String password = SMTP_AUTH_PWD;
           return new PasswordAuthentication(username, password);
        }
    }	

}
