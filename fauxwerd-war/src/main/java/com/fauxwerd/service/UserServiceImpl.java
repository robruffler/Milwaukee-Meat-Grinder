package com.fauxwerd.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.joda.time.DateTime;
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
import com.fauxwerd.model.Invite;
import com.fauxwerd.model.InviteStatus;
import com.fauxwerd.model.Role;
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
			
			user.getInvite().setStatus(InviteStatus.USED);
			user.getInvite().setDateUpdated(new DateTime());
			
			userDAO.updateInvite(user.getInvite());
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
	
	@Transactional
	public Role getRole(String name) {
		return userDAO.getRole(name);
	}
	
	@Transactional
	public List<User> listUsers() {
		return userDAO.listUsers();
	}
	
	@Transactional
	public void addInvite(Invite invite) {
		userDAO.addInvite(invite);
	}
	
	@Transactional
	public void updateInvite(Invite invite) {
		userDAO.updateInvite(invite);
	}
	
	@Transactional
	public Invite getInvite(String code) {
		return userDAO.getInvite(code);
	}
		
}

