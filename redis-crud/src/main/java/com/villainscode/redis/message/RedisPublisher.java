package com.villainscode.redis.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author CodeVillains
 * @description :
 */
@Slf4j
@Service
public class RedisPublisher {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisPublisher(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void sendMessage(String channel, String message) {
        log.info("# channel = {}, message = {}", channel, message);
        try {
            stringRedisTemplate.convertAndSend(channel, message);
            log.info("Message sent successfully to channel '{}'", channel);
        } catch (Exception e) {
            log.error("Failed to send message to channel '{}': {}", channel, e.getMessage());
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }
}
