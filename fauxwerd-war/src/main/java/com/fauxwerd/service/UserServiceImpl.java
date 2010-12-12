package com.fauxwerd.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fauxwerd.dao.UserDAO;
import com.fauxwerd.model.User;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDAO userDAO;

	private String createHash(String password) {
		MessageDigest messageDigest;
		
		try {
			messageDigest = MessageDigest.getInstance("sha");
			messageDigest.update(password.getBytes(), 0, password.length());
			String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
			if (hashedPass.length() < 32) {
				hashedPass = "0" + hashedPass;
			}

			return hashedPass;
		} 
		catch (NoSuchAlgorithmException e) {
			if (log.isErrorEnabled()) {
				log.error("", e);
			}
			return "";
		}
	}

	@Transactional
	public String createUser(User user) {
		if (user.getEmail() != null) {
			user.setPassword(createHash(user.getPassword()));
			user.fullyEnable();
						
			userDAO.saveUser(user);
		}
		return "success";
	}

	@Transactional
	public User getUser(String email) {
		return userDAO.getUser(email);
	}
	
	@Transactional
	public User getUserById(Long userId) {
		return userDAO.getUserById(userId);
	}

	// username = email
	@Transactional
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException, DataAccessException {
		User temp = getUser(arg0);
		return temp;
	}
		
}

