package com.leyoho.ecs.security.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Lists all trusted security user information.
 *  
 * @author Victor Feng
 */
public class SUTrustListHolder {
	private static final List<String> userTrustList = new ArrayList<String>();
	
	public void setTrustList(ArrayList<String> trustList) {
		userTrustList.clear();
		for (String s : trustList) {
			userTrustList.add(s);
		}
	}
	
	public static Boolean isTrustUser(String user) {
		return userTrustList.contains(user);
	}
}
