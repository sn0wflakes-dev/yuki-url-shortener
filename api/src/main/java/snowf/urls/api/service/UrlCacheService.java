package snowf.urls.api.service;

public interface UrlCacheService {
    void put(String shortUrl, String longUrl);
    String get(String shortUrl);
    void evict(String shortUrl);
}
