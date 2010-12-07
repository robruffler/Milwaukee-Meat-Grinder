package com.fauxwerd.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/iform")
public class IformController {

    final Log log = LogFactory.getLog(getClass());

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getIform(HttpServletRequest req, HttpServletResponse res, Model model) {
		String url = req.getParameter("url");
		model.addAttribute("url", url);
		ModelAndView modelAndView = new ModelAndView("iform");
		return modelAndView;
	}
}