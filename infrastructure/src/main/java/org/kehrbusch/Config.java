package org.kehrbusch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@Deprecated
public class Config {
    private static final Logger logger = Logger.getLogger(Config.class.getName());
/*
    @Autowired
    @Qualifier("properties")
    private Properties properties;

    @Bean
    public ReactiveStringRedisTemplate reactiveStringRedisTemplate(){
        return new ReactiveStringRedisTemplate(reactiveRedisConnectionFactory());
    }

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        logger.info("Redis host: " + this.properties.getProperty("redis.host"));
        redisStandaloneConfiguration.setHostName(this.properties.getProperty("redis.host"));
        redisStandaloneConfiguration.setPort(Integer.parseInt(this.properties.getProperty("redis.port")));
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }*/
}
