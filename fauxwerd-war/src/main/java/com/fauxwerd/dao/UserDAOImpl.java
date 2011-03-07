package com.fauxwerd.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fauxwerd.model.Invite;
import com.fauxwerd.model.Role;
import com.fauxwerd.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void saveUser(User entity) {
		sessionFactory.getCurrentSession().save(entity);
	}

	@SuppressWarnings("unchecked")
	public User getUser(String email) {
		List<User> users = sessionFactory.getCurrentSession().createQuery("select h from User h where email='" + email + "'").list();
	
		if (users.size() > 0)
			return users.get(0);
		else
			return null;
	}	

	public User getUserById(Long userId) {
		return (User) sessionFactory.getCurrentSession().get(User.class, userId);		
	}	
	
	public Role getRole(String name) {
		return (Role) sessionFactory.getCurrentSession().createQuery("from Role as role where role.name = ?")
		.setString(0, name)
		.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> listUsers() {
        return sessionFactory.getCurrentSession().createQuery("from User")
        .list();		
	}
	
	public void addInvite(Invite invite) {
		sessionFactory.getCurrentSession().save(invite);
	}
	
	public void updateInvite(Invite invite) {
		sessionFactory.getCurrentSession().update(invite);
	}
	
	public Invite getInvite(String code) {
		return (Invite)sessionFactory.getCurrentSession().createQuery("from Invite as invite where invite.code = ?")
		.setString(0, code)
		.uniqueResult();		
	}
	
}
