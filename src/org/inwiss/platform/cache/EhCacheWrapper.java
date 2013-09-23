package org.inwiss.platform.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.ehcache.*;

/**
 * 
 * @author Raidery
 * 
 * @param <K>
 * @param <V>
 */
@Component
public class EhCacheWrapper<K, V> implements CacheWrapper<K, V> {
	private String cacheName = null;
	@Autowired
	private CacheManager cacheManager = null;

	public EhCacheWrapper(final String cacheName,
			final CacheManager cacheManager) {
		this.cacheName = cacheName;
		this.cacheManager = cacheManager;
	}

	public EhCacheWrapper() {

	}

	public void put(final K key, final V value) {
		getCache().put(new Element(key, value));
	}

	public V get(final K key) {
		Element element = getCache().get(key);
		if (element != null) {
			return (V) element.getValue();
		}
		return null;
	}

	public Ehcache getCache() {
		return cacheManager.getEhcache(cacheName);
	}

	/**
	 * @return the cacheName
	 */
	public String getCacheName() {
		return cacheName;
	}

	/**
	 * @param cacheName the cacheName to set
	 */
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	/**
	 * @return the cacheManager
	 */
	public CacheManager getCacheManager() {
		return cacheManager;
	}

	/**
	 * @param cacheManager the cacheManager to set
	 */
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
}