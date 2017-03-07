/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.util;

/**
 * description:对class的一些操作
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7－10
 */
public abstract class ClassUtil {

	/**
	 * 通过字符串获取class
	 * 
	 * @param str
	 * @return
	 */
	public static Class<?> getClass(String str) {
		try {
			return Class.forName(str);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 通过class获取实例化对象
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 */
	public static <T> T instantiate(Class<T> t) {
		if (t == null) return null;
		try {
			return t.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过字符串直接获取实例化对象
	 * 
	 * @param <T>
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T instantiate(String str) {
		try {
			return (T) Class.forName(str).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取系统默认的ClassLoader
	 * 
	 * @return
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {}
		if (cl == null) {
			cl = ClassUtil.class.getClassLoader();
		}
		return cl;
	}

	/**
	 * 在当前包中查找
	 * 
	 * @param clazz
	 * @return
	 */
	public static String classPackageAsResourcePath(Class<?> clazz) {
		if (clazz == null) { return ""; }
		String className = clazz.getName();
		int packageEndIndex = className.lastIndexOf('.');
		if (packageEndIndex == -1) { return ""; }
		String packageName = className.substring(0, packageEndIndex);
		return packageName.replace('.', '/');
	}

	/**
	 * 重写cloassloader
	 * 
	 * @param classLoaderToUse
	 * @return
	 */
	public static ClassLoader overrideThreadContextClassLoader(ClassLoader classLoaderToUse) {
		Thread currentThread = Thread.currentThread();
		ClassLoader threadContextClassLoader = currentThread.getContextClassLoader();
		if (classLoaderToUse != null && !classLoaderToUse.equals(threadContextClassLoader)) {
			currentThread.setContextClassLoader(classLoaderToUse);
			return threadContextClassLoader;
		} else {
			return null;
		}
	}

}
