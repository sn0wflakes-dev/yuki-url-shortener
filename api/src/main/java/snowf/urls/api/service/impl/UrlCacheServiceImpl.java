package snowf.urls.api.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import snowf.urls.api.service.UrlCacheService;

import java.time.Duration;

@Service
public class UrlCacheServiceImpl implements UrlCacheService {

    private static final Logger log = LogManager.getLogger(UrlCacheServiceImpl.class);

    private final StringRedisTemplate redisTemplate;

    public UrlCacheServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(String shortUrl, String longUrl) {
        redisTemplate.opsForValue().set(shortUrl, longUrl, Duration.ofHours(3));
        log.info("Storing an url : {}", shortUrl);
    }

    @Override
    public String get(String shortUrl) {
        String cache = redisTemplate.opsForValue().get(shortUrl);
        if (cache != null) {
            log.info("Cached an url : {}", shortUrl);
            return cache;
        }
        return null;
    }

    @Override
    public void evict(String shortUrl) {
        log.warn("Delete an url from cache : {}", shortUrl);
        redisTemplate.delete(shortUrl);
    }
}
