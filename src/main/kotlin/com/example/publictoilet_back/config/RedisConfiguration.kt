package com.example.publictoilet_back.config

import lombok.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import java.time.Duration


@Configuration
class RedisConfiguration : CachingConfigurerSupport(){

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory? {
        return LettuceConnectionFactory("localhost", 6379)
    }

    override fun cacheManager(): CacheManager? {
        val builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory()!!)
        val configuration = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer())).prefixKeysWith("prefix:").entryTtl(Duration.ofMinutes(60))
        builder.cacheDefaults(configuration)
        return builder.build()
    }
}