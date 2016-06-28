package com.leyoho.ecs.security.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Lists all trusted security metadata sources.
 * 
 * @author Victor Feng
 *
 */
public class SMSTrustListHolder {
	private static final List<String> smsTrustList = new ArrayList<String>();
	
	public void setTrustList(ArrayList<String> trustList) {
		smsTrustList.clear();
		for (String s : trustList) {
			System.out.println("DMS " + s);
			smsTrustList.add(s);
		}
		System.out.println("smsTrustList size is " + smsTrustList.size());
	}
	
	public static Boolean isTrustSecurityMetadataSource(String sms) {
		return smsTrustList.contains(sms);
	}
}
