package com.leyoho.ecs.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class DmsUserDetailsUtil {
	
	/**
	 * Gets user details from current session, if no exists then return null
	 * 
	 * @return UserDetails
	 */
	public static UserDetails getUserDetails() {
		UserDetails userDetails = null;
		SecurityContext sc = SecurityContextHolder.getContext();
		Authentication ac = sc.getAuthentication();
		if (ac != null) {
			userDetails = (UserDetails) ac.getPrincipal();
		}
		return userDetails;
	}

	/**
	 * Obtains the current logged in user, if no exists then return null
	 * 
	 * @return loginId
	 */
	public static String getLoginUserName() {
		String loginId = null;
		UserDetails userDetails = getUserDetails();
		if (userDetails != null) {
			loginId = userDetails.getUsername();
		}
		return loginId;
	}

	/**
	 * Determines whether the user has logged in, if so then return true otherwise return false.
	 */
	public static boolean isLogined() {
		boolean flag = false;
		if(getLoginUserName() != null) flag = true;
		return flag;
	}
}
