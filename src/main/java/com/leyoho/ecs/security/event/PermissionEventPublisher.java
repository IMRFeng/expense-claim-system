package com.leyoho.ecs.security.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides a way to notify the IPermissionListener that the permission has been changed.
 * 
 * @author Victor Feng
 */
public class PermissionEventPublisher {
	private static Log logger = LogFactory.getLog(PermissionEventPublisher.class);

	private static Map<IPermissionListener, IPermissionListener> observerList = new HashMap<IPermissionListener, IPermissionListener>();

	/**
	 * Attaches a listener for permission event
	 * 
	 * @param subject
	 * @param listener
	 */
	public static void attach(IPermissionListener listener) {
		observerList.put(listener, listener);

		if (logger.isDebugEnabled()) {
			logger.debug("Added listener: " + listener.getClass().getName());
		}
	}

	/**
	 * Detaches from the event updater
	 * 
	 * @param listener
	 */
	public static void detach(IPermissionListener listener) {
		observerList.remove(listener);

		if (logger.isDebugEnabled()) {
			logger.debug("Removeded listener: " + listener.getClass().getName());
		}
	}

	/**
	 * Sends message to each listener.
	 * 
	 * @param eventSource
	 */
	public static void update(Class<?> eventSource) {
		if (logger.isDebugEnabled()) {
			logger.debug("permission changed from " + eventSource.getName());
		}

		Iterator<IPermissionListener> ita = observerList.keySet().iterator();
		while (ita.hasNext()) {
			IPermissionListener permissionListener = ita.next();
			permissionListener.updatePermission(eventSource);

			if (logger.isDebugEnabled()) {
				logger.debug("call update for listener="
						+ permissionListener.getClass().getName());
			}
		}
	}
}