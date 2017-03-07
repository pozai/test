/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.util;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * description:可用来判断参数的正确性
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7-10
 */
public abstract class Assert {

	/**
	 * 是否为true
	 * 
	 * @param expression
	 * @param message
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 是否为true
	 * 
	 * @param expression
	 */
	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	/**
	 * 是否为null
	 * 
	 * @param object
	 * @param message
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 是否为null
	 * 
	 * @param object
	 */
	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

	/**
	 * 不为空
	 * 
	 * @param object
	 * @param message
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 对象不能为空
	 * 
	 * @param object
	 */
	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}
	
	/**
     * 非负数
     * 
     * @param object
     */
    public static void notNegative(int number) {
        
        if (number < 0) {
            throw new IllegalArgumentException("[Assertion failed] - this argument is required; it must not be negative");
        }
    }

	/**
	 * 字符串是否有长度，包括空格
	 * 
	 * @param text
	 * @param message
	 */
	public static void hasLength(String text, String message) {
		if (!StringUtil.hasLength(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 字符串是否有长度，包括空格
	 * 
	 * @param text
	 */
	public static void hasLength(String text) {
		hasLength(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
	}

	/**
	 * 字符串是否有文本，不包括空格
	 * 
	 * @param text
	 * @param message
	 */
	public static void hasText(String text, String message) {
		if (!StringUtil.hasText(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 字符串是否有文本，不包括空格
	 * 
	 * @param text
	 */
	public static void hasText(String text) {
		hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
	}

	/**
	 * 在一个字符串中是否有包含子字符串
	 * 
	 * @param textToSearch
	 * @param substring
	 * @param message
	 */
	public static void doesNotContain(String textToSearch, String substring, String message) {
		if (StringUtil.hasLength(textToSearch) && StringUtil.hasLength(substring) && textToSearch.indexOf(substring) != -1) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 在一个字符串中是否有包含子字符串
	 * 
	 * @param textToSearch
	 * @param substring
	 */
	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring, "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
	}

	/**
	 * 对象数组是否为空
	 * 
	 * @param array
	 * @param message
	 */
	public static void notEmpty(Object[] array, String message) {
		if (ObjectUtil.isEmpty(array)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 对象数组是否为空
	 * 
	 * @param array
	 */
	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	/**
	 * 这个对象数组中的各个值都不空
	 * 
	 * @param array
	 * @param message
	 */
	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					throw new IllegalArgumentException(i + "索引数:" + message);
				}
			}
		}
	}

	/**
	 * 这个对象数组中的各个值都不空
	 * 
	 * @param array
	 */
	public static void noNullElements(Object[] array) {
		noNullElements(array, "[Assertion failed] - this must not contain any null elements");
	}

	/**
	 * 集合是否为空
	 * 
	 * @param collection
	 * @param message
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtil.isEmpty(collection)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 集合是否为空
	 * 
	 * @param collection
	 */
	public static void notEmpty(Collection<?> collection) {
		notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	/**
	 * map是否为空
	 * 
	 * @param map
	 * @param message
	 */
	public static void notEmpty(Map<Object, Object> map, String message) {
		if (CollectionUtil.isEmpty(map)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * map是否为空
	 * 
	 * @param map
	 */
	public static void notEmpty(Map<Object, Object> map) {
		notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}

	public static void isInstanceOf(Class<?> clazz, Object obj) {
		isInstanceOf(clazz, obj, "");
	}

	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			throw new IllegalArgumentException(message + "Object of class [" + (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type);
		}
	}

	public static void isAssignable(Class<?> superType, Class<?> subType) {
		isAssignable(superType, subType, "");
	}

	public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
		notNull(superType, "Type to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
		}
	}

	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(message);
		}
	}

	public static void state(boolean expression) {
		state(expression, "[Assertion failed] - this state invariant must be true");
	}

	/**
	 * 是否为null,并且长度不超过maxlength
	 * 
	 * @param object
	 * @param message
	 */
	public static void isNullAndMaxLength(Object object, int maxLength, String message) {
		if (StringUtil.isEmpty2(object) || object.toString().length() > maxLength) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 是否为数值
	 * 
	 * @param object
	 * @param message
	 */
	public static void isNumeric(String object, String message) {
		if (!StringUtil.isNumeric(object)) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/*  
	  * 判断是否为浮点数，包括double和float  
	  * @param str 传入的字符串  
	  * @return 是浮点数返回true,否则返回false  
	*/ 
	public static void isDouble(String object, String message) { 
	    Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");   
	    if(!pattern.matcher(object).matches()){
	    	throw new IllegalArgumentException(message);
	    }
	  }  
	
	public static void main(String[] args) {
		String aa = null;
		try {
			Assert.hasText("  ", "test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
