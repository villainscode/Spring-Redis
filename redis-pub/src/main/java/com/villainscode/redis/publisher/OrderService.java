package com.villainscode.redis.publisher;

import com.villainscode.redis.model.OrderQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * @author CodeVillains
 * @description :
 */
@Slf4j
@Service
public class OrderService {

    @Autowired
    @Qualifier("publisherRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ChannelTopic channelTopic;

    public Long publish(OrderQueue orderQueue) {
        log.info("# publish order queue = {}", orderQueue);
        return redisTemplate.convertAndSend(channelTopic.getTopic(), orderQueue);
    }
}
