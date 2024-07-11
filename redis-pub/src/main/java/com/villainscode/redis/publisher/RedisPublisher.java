package com.villainscode.redis.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;

/**
 * @author CodeVillains
 * @description :
 */
@Slf4j
@Configuration
public class RedisPublisher {
    @Value("${redis.publisher.topic}")
    private String topic;

    @Bean
    public ChannelTopic topic() {
        log.info("# topic = {}", topic);
        return new ChannelTopic(topic);
    }

}
