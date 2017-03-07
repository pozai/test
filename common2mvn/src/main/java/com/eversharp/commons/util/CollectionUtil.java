/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * description:集合常用操作类
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7-10
 */
public abstract class CollectionUtil {

	/**
	 * 是否为空
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * map是否为空
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<Object, Object> map) {
		return (map == null || map.isEmpty());
	}
	
	/**
	 * 数组转换为List
	 * 
	 * @param source
	 * @return
	 */
	public static List<?> arrayToList(Object source) {
		return Arrays.asList(ObjectUtil.toObjectArray(source));
	}
	
//	public static <T> T[] listToArray(Collection<T> collection){
//		return collection.toArray(Array.newInstance(T));
//	}

	/**
	 * 整合array到list中
	 * 
	 * @param array
	 * @param collection
	 */
	public static void mergeArrayIntoCollection(Object array, Collection<Object> collection) {
		if (collection == null) { throw new IllegalArgumentException("Collection must not be null"); }
		Object[] arr = ObjectUtil.toObjectArray(array);
		for (Object elem : arr) {
			collection.add(elem);
		}
	}

	/**
	 * 整合properties到map中
	 * 
	 * @param props
	 * @param map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void mergePropertiesIntoMap(Properties props, Map map) {
		if (map == null) { throw new IllegalArgumentException("Map must not be null"); }
		if (props != null) {
			for (Enumeration en = props.propertyNames(); en.hasMoreElements();) {
				String key = (String) en.nextElement();
				Object value = props.getProperty(key);
				if (value == null) {
					// Potentially a non-String value...
					value = props.get(key);
				}
				map.put(key, value);
			}
		}
	}

	/**
	 * 检查是否包含该元素
	 * 
	 * @param iterator
	 * @param element
	 * @return
	 */
	public static boolean contains(Iterator<?> iterator, Object element) {
		if (iterator != null) {
			while (iterator.hasNext()) {
				Object candidate = iterator.next();
				if (ObjectUtil.nullSafeEquals(candidate, element)) { return true; }
			}
		}
		return false;
	}

	public static boolean contains(Enumeration<?> enumeration, Object element) {
		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				Object candidate = enumeration.nextElement();
				if (ObjectUtil.nullSafeEquals(candidate, element)) { return true; }
			}
		}
		return false;
	}

	/**
	 * 比较包含 在集合里面的对象是否属于同一个（内存相同）
	 * 
	 * @param collection
	 * @param element
	 * @return
	 */
	public static boolean containsInstance(Collection<?> collection, Object element) {
		if (collection != null) {
			for (Object candidate : collection) {
				if (candidate == element) { return true; }
			}
		}
		return false;
	}

	public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
		if (isEmpty(source) || isEmpty(candidates)) { return false; }
		for (Object candidate : candidates) {
			if (source.contains(candidate)) { return true; }
		}
		return false;
	}

	public static Object findFirstMatch(Collection<?> source, Collection<?> candidates) {
		if (isEmpty(source) || isEmpty(candidates)) { return null; }
		for (Object candidate : candidates) {
			if (source.contains(candidate)) { return candidate; }
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T findValueOfType(Collection<?> collection, Class<T> type) {
		if (isEmpty(collection)) { return null; }
		T value = null;
		for (Object element : collection) {
			if (type == null || type.isInstance(element)) {
				if (value != null) {
					// More than one value found... no clear single value.
					return null;
				}
				value = (T) element;
			}
		}
		return value;
	}

	public static Object findValueOfType(Collection<?> collection, Class<?>[] types) {
		if (isEmpty(collection) || ObjectUtil.isEmpty(types)) { return null; }
		for (Class<?> type : types) {
			Object value = findValueOfType(collection, type);
			if (value != null) { return value; }
		}
		return null;
	}

	public static boolean hasUniqueObject(Collection<?> collection) {
		if (isEmpty(collection)) { return false; }
		boolean hasCandidate = false;
		Object candidate = null;
		for (Object elem : collection) {
			if (!hasCandidate) {
				hasCandidate = true;
				candidate = elem;
			}
			else if (candidate != elem) { return false; }
		}
		return true;
	}

	public static Class<?> findCommonElementType(Collection<?> collection) {
		if (isEmpty(collection)) { return null; }
		Class<?> candidate = null;
		for (Object val : collection) {
			if (val != null) {
				if (candidate == null) {
					candidate = val.getClass();
				}
				else if (candidate != val.getClass()) { return null; }
			}
		}
		return candidate;
	}

	public static <E> Iterator<E> toIterator(Enumeration<E> enumeration) {
		return new EnumerationIterator<E>(enumeration);
	}

	private static class EnumerationIterator<E> implements Iterator<E> {

		private Enumeration<E>	enumeration;

		public EnumerationIterator(Enumeration<E> enumeration) {
			this.enumeration = enumeration;
		}

		public boolean hasNext() {
			return this.enumeration.hasMoreElements();
		}

		public E next() {
			return this.enumeration.nextElement();
		}

		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("Not supported");
		}
	}
	
}
