package com.leyoho.ecs.util;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple class to manage loading the property file containing needed configuration data
 * from the package. Once loaded the configuration is held in memory as a singleton.  Since
 * we already require the application.properties file to support AWS Credentials, we use that
 * to store additional configuration values.
 */
public class Configuration {
	private static Configuration configuration = new Configuration();

	private Properties props = new Properties();

	private static final String AWS_CREDENTIALS = "/application.properties";

	private Logger logger = Logger.getLogger(Configuration.class.getName());

	private Configuration() {
		try {
			props.load(this.getClass().getResourceAsStream(AWS_CREDENTIALS));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unable to load configuration: " + e.getMessage(), e);
		}
	}

	public static final Configuration getInstance() {
		return configuration;
	}

	public String getProperty(String propertyName) {
		return props.getProperty(propertyName);
	}
}
