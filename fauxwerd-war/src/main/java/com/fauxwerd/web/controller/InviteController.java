package com.fauxwerd.web.controller;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fauxwerd.model.Invite;
import com.fauxwerd.service.UserService;

@Controller
public class InviteController {
	
	private static final Logger log = LoggerFactory.getLogger(InviteController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/admin/generate-invite-code")
	public ModelAndView generateInviteCode() {		
		String inviteCode = UUID.randomUUID().toString();
		inviteCode = StringUtils.remove(inviteCode, '-');
		
		if (log.isDebugEnabled()) log.debug(String.format("inviteCode = %s", inviteCode));
	
		Invite invite = new Invite(inviteCode);
		
		userService.addInvite(invite);
				
		return new ModelAndView("admin/invite-code", "invite", invite);
	}

}
