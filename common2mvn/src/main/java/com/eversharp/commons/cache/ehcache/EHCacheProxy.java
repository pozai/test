/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.cache.ehcache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eversharp.commons.cache.ICache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;


/**
 * description:ehcache的实现
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7-10
 */
public class EHCacheProxy<K, V> implements ICache<K, V> {

	private Cache	ehcache;

	public EHCacheProxy(Cache ehache) {
		this.ehcache = ehache;
	}

	@Override
	public void init() {

	}

	@Override
	public void add(K key, V value) {
		Element element = new Element(key, value);
		ehcache.put(element);
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) {
		Element e = ehcache.get(key);

		if (e != null) return (V) e.getObjectValue();
		else return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<K, V> get(K[] keys) {
		Map<K, V> map = new HashMap<K, V>();

		for (int i = 0; i < keys.length; i++) {
			Element e = ehcache.get(keys[i]);

			if (e != null) map.put(keys[i], (V) e.getObjectValue());
		}

		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<K,V> getMap(){
		Map<K, V> map = new HashMap<K, V>();
		
		List<K> keyList= ehcache.getKeys();
		for (K key:keyList) {
			Element e = ehcache.get(key);

			if (e != null) map.put(key, (V) e.getObjectValue());
		}

		return map;
	}

	/**
	 * 当添加时会自动判断是否已经存在
	 */
	@Override
	public void set(K key, V value) {
		Element element = new Element(key, value);
		ehcache.put(element);
	}

	@Override
	public void remove(K key) {

		ehcache.remove(key);
	}

	@Override
	public void removeAll() {

		ehcache.removeAll();
	}

	@Override
	public void destroy() {}

	public Cache getCache() {

		return ehcache;
	}

	@Override
	public void set(K key, V value, int expire) {
		Element element = new Element(key, value);
		element.setTimeToLive(expire);
		
		ehcache.put(element);
	}

}