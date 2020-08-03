package com.web.blog.config.redis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * Embedded Redis Configration
 *
 */
@Component
public class EmbeddedRedisConfiguration {

   @Value("${spring.redis.port}")
   private int redisPort;

   private RedisServer redisServer;

   @PostConstruct
   public void startRedis() throws IOException {
      redisServer = new RedisServer(redisPort);
      redisServer.start(); //Redis 시작
   }

   @PreDestroy
   public void stopRedis() {
      redisServer.stop(); //Redis 종료
   }

}