/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.cache;

import java.util.Map;

import com.eversharp.commons.cache.ehcache.EHCacheManager;
import com.eversharp.commons.cache.memcached.MemCacheManager;
import com.eversharp.commons.util.Assert;

/**
 * description:获取缓存的工厂
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2011-7-3
 */
public final class CacheFactory {
	
	/**
	 * 用于session的cache
	 * 
	 * @return
	 */
	public static ICache<String, Map<String, Object>> getSessionCache() {

		return new EHCacheManager().createCache("");
	}

	/**
	 * 获取3595的本地缓存
	 * 
	 * @return
	 */
	public static ICache<String,Object> getLocalCache(String name) {
		Assert.notNull(name, "cache对应的名称不能为空!!!");

		return new EHCacheManager().createCache(name);
	}
	
	/**
	 * 获取3595的memcache
	 * 
	 * @return
	 */
    public static ICache<String,Object> getMemCache(String name) {
		Assert.notNull(name, "cache对应的名称不能为空!!!");

        return new MemCacheManager().createCache(name);
    }
}
