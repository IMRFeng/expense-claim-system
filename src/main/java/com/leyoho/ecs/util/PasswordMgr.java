package com.leyoho.ecs.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class PasswordMgr {

	private String algorithm;

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String digestMessage(byte[] originalData) {
		String digestData = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(originalData);
			digestData = new String(Base64.encodeBase64(messageDigest.digest()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return digestData;
	}

	public boolean verifyDigest(byte[] originalData, String digestData) {
		String result = digestMessage(originalData);
		return (digestData != null) ? digestData.equals(result) : (null == result);
	}
}
