package com.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class CacheServiceImpl implements CacheService {
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${redis.cache.product}")
    public String PRODUCT_CACHE;

    @Override
    public void clearCache() {
        Cache cache = cacheManager.getCache(PRODUCT_CACHE);  // Use the cache name you want to clear

        Set<String> keys = redisTemplate.keys("*");

        if (keys == null || keys.isEmpty()) {
            log.info("No cache entries found");
            return;
        }

        log.info("---- Redis Cache Entries in {} ----", cache.getName());
        for (String key : keys) {
            String value = redisTemplate.opsForValue().get(key);
            log.info("Key: {}, Value: {}", key, value);
        }





        if (cache != null) {
            cache.clear();  // Clear all cache entries for "PRODUCT_CACHE"
            log.info("Cache cleared successfully!");
        } else {
            log.info("Cache not found!");
        }
    }
}
