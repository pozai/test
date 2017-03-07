/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.cache;

import java.util.Map;

/**
 * description:对接口的抽象描述
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2011-7-2
 */
public interface ICache<K, V> {

	/**
	 * prepare cache condition
	 */
	public void init();

	/**
	 * add cache to memory
	 * 
	 * @param key
	 * @param value
	 */
	public void add(K key, V value);

	/**
	 * get a cache by key
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key);

	/**
	 * get caches by keys
	 * 
	 * @param keys
	 * @return
	 */
	public Map<K, V> get(K[] keys);

	/**
	 * get all caches
	 * 
	 * @return
	 */
	public Map<K, V> getMap();

	/**
	 * modify cache by key
	 * 
	 * @param key
	 * @param value
	 */
	public void set(K key, V value);
	
	/**
	 * 可具体设置过期时间
	 * 
	 * @param key
	 * @param value
	 * @param expire从创建到过期的时间,以秒为单位计算
	 */
	public void set(K key,V value,int expire);

	/**
	 * remove cache by key
	 * 
	 * @param key
	 */
	public void remove(K key);

	/**
	 * remove all cache from memory
	 */
	public void removeAll();

	/**
	 * 
	 * 
	 */
	public void destroy();
}
