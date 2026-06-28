//package com.example.otclients.config.redis;
//
//import com.example.otclients.config.AppInfo;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//
//@Configuration
//@EnableCaching
//@RequiredArgsConstructor
//@Slf4j
//public class RedisConfig {
//
//    private final AppInfo appInfo;
//    //    private final String  HOST = "localhost";
//    //    private final String PASSWORD = "localhost";
//    //    private final int PORT = 26379;
//    //    public static final int TIME_TO_LIVE = 1440; //#1 day;
//
//    @Bean
//    protected LettuceConnectionFactory getConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration =
//                new RedisStandaloneConfiguration(appInfo.getRedisHost(),
//                        appInfo.getRedisPort());
//        char[] redisPassword = appInfo.getRedisPassword().toCharArray();
//
//        // Set the password only if it's present (local may not have a password)
//        if (redisPassword != null && redisPassword.length > 0) {
//            redisStandaloneConfiguration.setPassword(appInfo.getRedisPassword());
//        }
//
//        LettuceConnectionFactory redisConnectionFactory =
//                new LettuceConnectionFactory(redisStandaloneConfiguration);
//        redisConnectionFactory.start();
//        validateRedisConnection(redisConnectionFactory);
//        return redisConnectionFactory;
//    }
//
//
//    //for spring caches
//    //using @Cacheable(cacheNames = name)
////    private RedisCacheConfiguration getDefaultCacheConfig() {
////        return RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(
////                        RedisSerializationContext.SerializationPair.fromSerializer(
////                                new GenericJackson2JsonRedisSerializer()))
////                .entryTtl(Duration.ofMinutes(appInfo.getRedisTimeToLive())).disableCachingNullValues();
////    }
////    @Bean
////    protected RedisCacheManager redisStarter() {
////        return RedisCacheManager.RedisCacheManagerBuilder
////                .fromConnectionFactory(getConnectionFactory())
////                .withCacheConfiguration(some name,
////                        getDefaultCacheConfig()).build();
////    }
//
////    @Bean
////    protected RedisTemplate<String, DashboardOneResponse> covidTotals() {
////        RedisTemplate<String, DashboardOneResponse> redisTemplate = new RedisTemplate<>();
////        redisTemplate.setConnectionFactory(getConnectionFactory());
////        redisTemplate.setKeySerializer(new StringRedisSerializer());
////        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
////        return redisTemplate;
////    }
//
//    private void validateRedisConnection(RedisConnectionFactory connectionFactory) {
//        log.info("Trying to ping redis");
//        try (RedisConnection conn = connectionFactory.getConnection()) {
//            String pong = conn.ping();
//            log.info("Ping Response Received: {}", pong);
//            if (!"PONG".equals(pong)) { throw new IllegalStateException("Unexpected PING response: " + pong); }
//        } catch (Exception e) {
//            throw new IllegalStateException("Failed to connect to Redis at "
//                    +
//                    appInfo.getRedisHost() + ":" + appInfo.getRedisPort(), e);
//        }
//
//    }
//}
