package com.leyoho.ecs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.leyoho.ecs.security.util.DmsUserDetailsUtil;
import com.leyoho.ecs.service.IHomePageService;
import com.leyoho.ecs.util.SecurityUserHolder;

@Controller
public class HomePageController {
	private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);
	
	@Autowired
	private IHomePageService homePageService;
	 
	protected static final String VIEW_NAME_HOMEPAGE = "index";
	
	@RequestMapping(value="/home", method=RequestMethod.HEAD)
    public void doHealthCheck(HttpServletResponse response) {
        response.setContentLength(0);
        response.setStatus(HttpServletResponse.SC_OK);
    }
	
	@RequestMapping(value="/dashboard")
	public String index(Model model) {
		User user = SecurityUserHolder.getCurrentUser();

		logger.info("Welcome dashboard! The logged in client is {}.", user.getUsername());
		
        model.addAttribute("loggedUserName", user.getUsername() );
        
        List<String> securityResource = homePageService.getSecurityResources(user.getUsername());
        System.out.println("Resources size is " + securityResource.size());
        
		return VIEW_NAME_HOMEPAGE;
	}
	
	@RequestMapping(value="/aboutUs")
	public String aboutUs(Model model) {
		User user = SecurityUserHolder.getCurrentUser();

		logger.info("Welcome dashboard! The logged in client is {}.", user.getUsername());
		
        model.addAttribute("loggedUserName", user.getUsername() );
        
        List<String> securityResource = homePageService.getSecurityResources(user.getUsername());
        System.out.println("Resources size is " + securityResource.size());
        
		return "aboutUs";
	}
	
	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap model) {
		String loginName = DmsUserDetailsUtil.getLoginUserName();
		System.out.println("Index page, The user name is " + loginName);
		if (loginName == null) return "login";

		User user = SecurityUserHolder.getCurrentUser();
		System.out.println("The user name: " + user.getUsername());
		List<String> securityResource = homePageService.getSecurityResources(loginName);
		request.getSession().setAttribute("securityResource", securityResource);
		request.setAttribute("welcomewords", "Hello " + DmsUserDetailsUtil.getLoginUserName()+ ", "
				+ "you have roles:" + DmsUserDetailsUtil.getUserDetails().getAuthorities());
		return VIEW_NAME_HOMEPAGE;
	}
}
