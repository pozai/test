/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

/**
 * description:可以用这个classloader来加载相应的src下的资源文件
 * 
 * @author wu_quanyin
 * @version 1.0
 * @date 2011-7－10
 */
public class ClassLoaderUtil {

	/**
	 * 根据多种可能来进行查找资源文件
	 * 
	 * @param resourceName
	 * @param callingClass
	 * @return
	 */
	public static URL getResource(String resourceName, Class<?> callingClass) {

		resourceName = resourceName.startsWith("/") ? resourceName.substring(1) : resourceName;

		URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);

		if (url == null) {
			ClassLoader cl = ClassLoaderUtil.class.getClassLoader();
			url = cl.getResource(resourceName);

		}

		if (url == null && callingClass != null) {
			ClassLoader cl = callingClass.getClassLoader();

			if (cl != null) {
				url = cl.getResource(resourceName);
			}

			if (url == null) {// find resource by class path
				url = cl.getResource(ClassUtil.classPackageAsResourcePath(callingClass) + "/" + resourceName);
			}
		}

		return url;
	}

	/**
	 * 获取资源文件并转为流的形式
	 * 
	 * @param resourceName
	 * @param callingClass
	 * @return
	 */
	public static InputStream getResourceAsStream(String resourceName, Class<?> callingClass) {
		URL url = getResource(resourceName, callingClass);

		try {
			return (url != null) ? url.openStream() : null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 获取properties文件
	 * 
	 * @param resourceName
	 * @param callingClass
	 * @return
	 */
	public static Properties getResourceAsProperties(String resourceName, Class<?> callingClass) {
		if (!resourceName.endsWith(".properties")) { throw new IllegalArgumentException("不是合法的.properties文件!"); }

		Properties properties = new Properties();
		try {
			InputStream inputStream = getResourceAsStream(resourceName, callingClass);
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
			properties.load(bf);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return properties;
	}

	/**
	 * 加载根据properties 规定格式的xml文件
	 * 
	 * @param resourceName
	 * @param callingClass
	 * @return
	 */
	public static Properties getResourceAsXMLProperties(String resourceName, Class<?> callingClass) {
		if (!resourceName.endsWith(".xml")) { throw new IllegalArgumentException("不是合法的.xml文件!"); }

		Properties properties = new Properties();
		try {
			properties.loadFromXML(getResourceAsStream(resourceName, callingClass));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return properties;

	}

	/**
	 * Load a class with a given name.
	 */
	public static Class<?> loadClass(String className, Class<?> callingClass) throws ClassNotFoundException {
		try {
			return Thread.currentThread().getContextClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException ex) {
				try {
					return ClassLoaderUtil.class.getClassLoader().loadClass(className);
				} catch (ClassNotFoundException exc) {
					try {
						return callingClass.getClassLoader().loadClass(className);
					} catch (ClassNotFoundException excn) {
						return callingClass.getClassLoader().loadClass(ClassUtil.classPackageAsResourcePath(callingClass) + "/" + className);
					}
				}
			}
		}
	}

}
