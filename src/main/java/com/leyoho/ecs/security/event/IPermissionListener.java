package com.leyoho.ecs.security.event;

/**
 * Defines a method to listen the permission update event
 *  
 * @author Victor Feng
 *
 */
public interface IPermissionListener {
	public void updatePermission(Class<?> eventSource);
}
