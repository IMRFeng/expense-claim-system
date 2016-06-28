package com.leyoho.ecs.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.leyoho.ecs.security.dao.DmsJdbcDao;
import com.leyoho.ecs.security.event.IPermissionListener;
import com.leyoho.ecs.security.event.PermissionEventPublisher;

public class DmsInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, IPermissionListener {

	private boolean rejectPublicInvocations = false;
	private DmsJdbcDao secDao;

	private static Map<String, Integer> resources = new HashMap<String, Integer>();
	
	public DmsInvocationSecurityMetadataSource(DmsJdbcDao dao) {
		this.secDao = dao;
		loadSecurityMetadataSource();
		PermissionEventPublisher.attach(this);
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = ((FilterInvocation)object).getRequestUrl();
		
		// Gets the URL value from request URL which includes parameters
		int index = url.indexOf("?");
        if (index != -1) {
            url = url.substring(0, index);
        }
        
		if(resources.isEmpty()) return null;
		
		Integer resourceId = resources.get(url);
		
		if(rejectPublicInvocations && resourceId == null) {// The request does not exist
			throw new IllegalArgumentException("Secure object invocation " + object +
                    " was denied as public invocations are not allowed via this interceptor. ");
		}
		
		return getRolesByResouceId(resourceId);
	}
	
	/**
	 * Obtains roles via resource id.
	 * @param resourceId
	 * @return
	 */
	private Collection<ConfigAttribute> getRolesByResouceId(Integer resourceId) {
		List<String>  roles = secDao.getRoleByResourceId(resourceId);
		
		Collection<ConfigAttribute> atts = null;
		if(roles != null) {
			atts = new ArrayList<ConfigAttribute>();
			for (String role : roles) {
				atts.add(new SecurityConfig(role));
			}
		}
		
		return atts;
	}
	
	private void loadSecurityMetadataSource() {
		List<Map<String, Object>> resourceDtos = secDao.getAllResource();
		if(resourceDtos != null) {
			resources.clear();
			for (Map<String, Object> dto : resourceDtos) {
				resources.put(dto.get("url").toString(), Integer.parseInt(dto.get("id").toString())); 
			}
		}
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}
	
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	public void setSecDao(DmsJdbcDao dao) {
		this.secDao = dao;
	}

	public void setRejectPublicInvocations(boolean rejectPublicInvocations) {
		this.rejectPublicInvocations = rejectPublicInvocations;
	}

	@Override
	public void updatePermission(Class<?> eventSource) {
		resources = null;
	}
}
