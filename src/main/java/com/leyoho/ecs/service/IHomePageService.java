package com.leyoho.ecs.service;

import java.util.List;

public interface IHomePageService {

	/**
	 * Returns all security resources.
	 * @return
	 */
	public List<String> getSecurityResources(String userName);
}
