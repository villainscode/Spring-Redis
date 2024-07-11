package com.villainscode.redis.subscriber;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author CodeVillains
 * @description :
 */
@Slf4j
@Service
public class SubscriberEventListener implements MessageListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("subscriberRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            log.info("# new message received = {}", message);
            OrderDTO orderDTO = objectMapper.readValue(message.getBody(), OrderDTO.class);
        } catch (IOException e) {
            log.error("# Subscriber Event Error = {}", message);
            throw new RuntimeException(e);
        }
    }
}
