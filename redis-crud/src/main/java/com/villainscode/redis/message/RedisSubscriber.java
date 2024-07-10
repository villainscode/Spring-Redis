package com.villainscode.redis.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

/**
 * @author CodeVillains
 * @description :
 */
@Slf4j
@Service
public class RedisSubscriber implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String receiveMessage = new String(message.getBody());
        String channel = new String(message.getChannel());

        log.info("Received message '{}' from channel '{}'", message, channel);
        if ("myChannel".equals(channel)) {
            log.info("Received message from 'myChannel': {}", message);
            // Add assertions or additional actions based on the received message
        }
    }
}
