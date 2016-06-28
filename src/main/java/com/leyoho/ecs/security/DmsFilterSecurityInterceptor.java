package com.leyoho.ecs.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.leyoho.ecs.security.util.DmsUserDetailsUtil;
import com.leyoho.ecs.security.util.SMSTrustListHolder;
import com.leyoho.ecs.security.util.SUTrustListHolder;
import com.leyoho.ecs.util.DmsUtil;

public class DmsFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter,
						WebInvocationPrivilegeEvaluator{

	private FilterInvocationSecurityMetadataSource securityMetadataSource;
	private static Log logger = LogFactory.getLog(DmsFilterSecurityInterceptor.class);
	private List<WebInvocationPrivilegeEvaluator> webInvocationPrivilegeEvaluators = new ArrayList<>();
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		request.setAttribute(WebAttributes.WEB_INVOCATION_PRIVILEGE_EVALUATOR_ATTRIBUTE, this);
		String url = httpRequest.getRequestURI().replaceFirst(httpRequest.getContextPath(), "");
		
		//	Judge the login status, if not logged in then redirect to login page
		if(!DmsUserDetailsUtil.isLogined()) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
			logger.info("Not logged in user, From IP:" + DmsUtil.getRequestIp(httpRequest) + " attempt to access URI " + url);
			return;
    	}
		
		//	Filter white list
		if(SUTrustListHolder.isTrustUser(DmsUserDetailsUtil.getLoginUserName())) {
			chain.doFilter(request, response);
			return;
		}
		
		//	Filter white list URL
		if(SMSTrustListHolder.isTrustSecurityMetadataSource(url)){
			chain.doFilter(request, response);
			return;
		}
		
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}
	
	public void invoke(FilterInvocation fi) throws IOException, ServletException {
		InterceptorStatusToken token = null;
		try {
			token = super.beforeInvocation(fi);
		} catch (IllegalArgumentException e) {
			HttpServletRequest httpRequest = fi.getRequest();
			HttpServletResponse httpResponse = fi.getResponse();
			String url = httpRequest.getRequestURI().replaceFirst(httpRequest.getContextPath(), "");
			logger.info("The user " + DmsUserDetailsUtil.getLoginUserName() + ", From IP:"
					+ DmsUtil.getRequestIp(httpRequest) + " attempt to access unauthorized URI: " + url);

			httpResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/error/406.jsp");
			dispatcher.forward(httpRequest, httpResponse);
			return;
		}

		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	@Override
	public void destroy() {
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}
	
	@Override
    public boolean isAllowed(String uri, Authentication authentication) {
        for (WebInvocationPrivilegeEvaluator privilegeEvaluator : webInvocationPrivilegeEvaluators) {
            if (!privilegeEvaluator.isAllowed(uri, authentication)) {
                return false;
            }
        }
        return true;
    }
	
	@Override
    public boolean isAllowed(String contextPath, String uri, String method,
            Authentication authentication) {
        for (WebInvocationPrivilegeEvaluator privilegeEvaluator : webInvocationPrivilegeEvaluators) {
            if (!privilegeEvaluator.isAllowed(contextPath, uri, method, authentication)) {
                return false;
            }
        }
        return true;
    }

//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName)
//            throws BeansException {
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName)
//            throws BeansException {
//        if (bean instanceof WebInvocationPrivilegeEvaluator
//                && !bean.getClass().isAssignableFrom(getClass())) {
//            webInvocationPrivilegeEvaluators.add((WebInvocationPrivilegeEvaluator) bean);
//        }
//        return bean;
//    }
	
	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return this.securityMetadataSource;
	}
	
	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
		this.securityMetadataSource = newSource;
	}

}
