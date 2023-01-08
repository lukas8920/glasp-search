package org.kehrbusch.services;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class RateLimitService {
    private static final Logger logger = Logger.getLogger(RateLimitService.class.getName());

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @Value("${max.search.requests}")
    private int maxNoOfSearchRequests;

    public Bucket resolveBucket(){
        String user = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return this.cache.computeIfAbsent(user, this::newBucket);
    }

    private Bucket newBucket(String user){
        Bandwidth bandwidth = Bandwidth.classic(maxNoOfSearchRequests, Refill.intervally(maxNoOfSearchRequests, Duration.ofMinutes(1)));
        return Bucket.builder()
                .addLimit(bandwidth)
                .build();
    }
}
