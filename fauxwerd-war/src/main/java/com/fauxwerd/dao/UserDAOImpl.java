package com.fauxwerd.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fauxwerd.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void saveUser(User entity) {
		sessionFactory.getCurrentSession().save(entity);
	}
	//TODO remove commented out methods
/*
	@SuppressWarnings("unchecked")
	public User getUser(String username) {
		List<User> users = sessionFactory.getCurrentSession().createQuery("select h from User h where username='" + username + "'").list();
	
		if (users.size() > 0)
			return users.get(0);
		else
			return null;
	}	
*/

	@SuppressWarnings("unchecked")
	public User getUser(String email) {
		List<User> users = sessionFactory.getCurrentSession().createQuery("select h from User h where email='" + email + "'").list();
	
		if (users.size() > 0)
			return users.get(0);
		else
			return null;
	}	
	
}
