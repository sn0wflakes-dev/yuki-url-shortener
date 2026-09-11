package snowf.urls.api.service;

import snowf.urls.api.dto.RedirectUrlRequest;
import snowf.urls.api.dto.ShortenUrlRequest;
import snowf.urls.api.dto.ShortenUrlResponse;

public interface UrlShortenerService {
    ShortenUrlResponse shortenUrl(ShortenUrlRequest request);
    String retrieveUrl(RedirectUrlRequest shortUrl);
}
