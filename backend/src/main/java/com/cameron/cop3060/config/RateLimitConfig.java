package com.cameron.cop3060.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Rate Limiting Configuration using Bucket4j
 * Prevents exceeding API rate limits
 * 
 * @author Cameron Brown
 */
@Configuration
public class RateLimitConfig {

    @Value("${tmdb.api.rate-limit:40}")
    private int tmdbRateLimit;

    @Value("${randomuser.api.rate-limit:100}")
    private int randomUserRateLimit;

    // Store rate limiters for different APIs
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Bean
    public Bucket tmdbRateLimiter() {
        Bandwidth limit = Bandwidth.classic(tmdbRateLimit, Refill.intervally(tmdbRateLimit, Duration.ofSeconds(10)));
        Bucket bucket = Bucket.builder().addLimit(limit).build();
        buckets.put("tmdb", bucket);
        return bucket;
    }

    @Bean
    public Bucket randomUserRateLimiter() {
        Bandwidth limit = Bandwidth.classic(randomUserRateLimit, Refill.intervally(randomUserRateLimit, Duration.ofMinutes(1)));
        Bucket bucket = Bucket.builder().addLimit(limit).build();
        buckets.put("randomuser", bucket);
        return bucket;
    }

    public Bucket getBucket(String apiName) {
        return buckets.get(apiName);
    }
}
