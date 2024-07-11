package com.villainscode.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author CodeVillains
 * @description :
 */
@Configuration
public class SubscriberConfiguration {

    private final MessageListener messageListener;
    private final RedisConnectionFactory redisConnectionFactory;

    public SubscriberConfiguration(MessageListener messageListener, RedisConnectionFactory redisConnectionFactory) {
        this.messageListener = messageListener;
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(ChannelTopic topic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListener, topic);
        return container;
    }
}

