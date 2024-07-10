package com.villainscode.redis.rediskey;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.villainscode.redis.keygenerate.KeyGenerateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author CodeVillains
 * @description :
 */
@Slf4j
@Service
public class RedisKeyService {

    private final RedisTemplate<String, String> redisTemplate;
    private KeyGenerateService keyGenerateService;

    public RedisKeyService(RedisTemplate<String, String> redisTemplate, KeyGenerateService keyGenerateService) {
        this.redisTemplate = redisTemplate;
        this.keyGenerateService = keyGenerateService;
    }

    public Set<String> getAllKeys() {
        return redisTemplate.keys("*");
    }

    public Map<String, Object> getAllKeysAndValues() {
        Set<String> keys = redisTemplate.keys("*");
        Map<String, Object> keyValueMap = new HashMap<>();
        if (keys != null) {
            for (String key : keys) {
                try {
                    log.debug("Processing key: {}", key);
                    String type = redisTemplate.type(key).code();
                    Object value = null;
                    switch (type) {
                        case "string":
                            value = redisTemplate.opsForValue().get(key);
                            break;
                        case "list":
                            value = redisTemplate.opsForList().range(key, 0, -1);
                            break;
                        case "set":
                            value = redisTemplate.opsForSet().members(key);
                            break;
                        case "zset":
                            value = redisTemplate.opsForZSet().range(key, 0, -1);
                            break;
                        case "hash":
                            value = redisTemplate.opsForHash().entries(key);
                            break;
                        default:
                            value = "Unsupported data type";
                            break;
                    }
                    keyValueMap.put(key, value);
                } catch (Exception e) {
                    log.error("Error processing key: {}", key, e);
                }
            }
        }
        return keyValueMap;
    }

    private Map<String, String> getValuesByKeys(Set<String> keys) {
        Map<String, String> keyValueMap = new HashMap<>();
        if (keys != null) {
            for (String key : keys) {
                String value = redisTemplate.opsForValue().get(key);
                keyValueMap.put(key, value);
            }
        }
        return keyValueMap;
    }

    @CacheEvict(value = "cacheKey", key = "#number + #value")
    public void save(Integer number, String value) throws NoSuchMethodException {
        String key = keyGenerateService.generateKey(number, value);
        redisTemplate.opsForValue().set(key, "Example Data for " + key);
        log.info("# Stored data in Redis with key : {}", key);
    }

    @Cacheable(value = "cacheKey", key = "#number + #value")
    public String search(Integer number, String value) throws NoSuchMethodException {
        String key = keyGenerateService.generateKey(number, value);
        String storedData = redisTemplate.opsForValue().get(key);
        log.info("# Retrieved data from Redis with key : {}", key);
        return storedData;
    }

    @CacheEvict(value = "cacheKey", key = "#number + #value")
    public void edit(Integer number, String newValue) throws NoSuchMethodException {
        String key = keyGenerateService.generateKey(number, newValue);
        // edit
        redisTemplate.opsForValue().set(key, newValue);
        log.info("# Edited data in Redis with key : {}", key);
    }

    @CacheEvict(value = "cacheKey", key = "#number + #value")
    public void delete(Integer number, String value) throws NoSuchMethodException {
        String key = keyGenerateService.generateKey(number, value);
        // delete
        Boolean deleted = redisTemplate.delete(key);
        log.info("# Deleted data in Redis with key : {}", key);
    }

}
