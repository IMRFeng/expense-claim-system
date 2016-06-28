package com.leyoho.ecs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leyoho.ecs.security.dao.DmsJdbcDao;
import com.leyoho.ecs.service.IHomePageService;

@Service(value = "homePageService")
public class HomePageServiceImpl implements IHomePageService {

	@Autowired
	private DmsJdbcDao dmsSecurityDao;
	
	@Override
	public List<String> getSecurityResources(String userName) {
		return dmsSecurityDao.getResourceByUserName(userName);
	}
}
