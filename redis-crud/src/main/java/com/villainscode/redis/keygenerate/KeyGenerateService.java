package com.villainscode.redis.keygenerate;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Service;

/**
 * @author CodeVillains
 * @description :
 */
@Slf4j
@Service
public class KeyGenerateService {
    private final KeyGenerator keyGenerator;

    public KeyGenerateService(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public void setKeyGenerator(Integer number, String value) throws NoSuchMethodException {
        // RedisConfig에서 정의한 KeyGenerator를 사용하여 키 생성
        Object generatedKey = keyGenerator.generate(this, getClass().getDeclaredMethod("setKeyGenerator", Integer.class, String.class), number, value);
        // 생성된 키를 Redis 또는 다른 용도로 사용
        log.info("Generated Redis Key : {}" + generatedKey);
    }

    public String generateKey(Integer number, String value) throws NoSuchMethodException {
        Method method = getClass().getDeclaredMethod("setKeyGenerator", Integer.class, String.class);
        Object generatedKey = keyGenerator.generate(this, method, number, value);
        log.info("# generatedKey = {}", generatedKey);
        return generatedKey.toString();
    }
}
