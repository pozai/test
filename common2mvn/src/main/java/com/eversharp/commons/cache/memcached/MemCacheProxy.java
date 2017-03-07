/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.cache.memcached;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.eversharp.commons.cache.ICache;
import com.eversharp.commons.util.Assert;
import com.whalin.MemCached.MemCachedClient;


/**
 * description:memcache的实现
 * 
 * @author qingpozhang(fz.qingpozhang@gmail.com)
 * @version 1.0
 * @date 2013-1-9
 */
public class MemCacheProxy<K, V> implements ICache<K, V> {

	private MemCachedClient	mcc;

	public MemCacheProxy(MemCachedClient mcc) {
		this.mcc = mcc;
	}

	@Override
	public void init() {

	}

	@Override
	public void add(K key, V value) {
	    Assert.notNull(key);
        Assert.notNull(value);
        
	    mcc.add(key.toString(), value);
	}
	
	public void add(K key, V value, Date expiry) {
	    Assert.notNull(key);
        Assert.notNull(value);
        Assert.notNull(expiry);
        
        mcc.add(key.toString(), value, expiry);
    }

	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) {
	    Assert.notNull(key);
	    
	    V value = (V) mcc.get(key.toString());
		if (value != null) return value;
		else return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<K, V> get(K[] keys) {
	    Assert.notNull(keys);
	    
		Map<K, V> map = new HashMap<K, V>();

		for (int i = 0; i < keys.length; i++) {
		    V value = (V) mcc.get(keys[i].toString());

			if (value != null) map.put(keys[i], value);
		}

		return map;
	}
	

	/**
	 * 当添加时会自动判断是否已经存在
	 */
	public boolean exist(K key){   
        Assert.notNull(key);
        return mcc.keyExists(key.toString());
    }   
	@Override
	public void set(K key, V value) {
	    Assert.notNull(key);
	    Assert.notNull(value);
		mcc.set(key.toString(), value);
	}

	@Override
    public void set(K key, V value, int expire) {
	    Assert.notNull(key);
        Assert.notNull(value);
        mcc.set(key.toString(), value, new Date(expire*1000));
    }
	
	@Override
	public void remove(K key) {
	    Assert.notNull(key);
	    mcc.delete(key.toString());
	}

	@Override
	public void destroy() {}

	
	public MemCachedClient getMemCacheClient() {

		return mcc;
	}

    @Override
    public Map<K, V> getMap() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeAll() {
        // TODO Auto-generated method stub
        mcc.flushAll();
    }

}