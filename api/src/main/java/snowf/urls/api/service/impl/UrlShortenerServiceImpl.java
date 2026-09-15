package snowf.urls.api.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import snowf.urls.api.dto.RedirectUrlRequest;
import snowf.urls.api.dto.ShortenUrlRequest;
import snowf.urls.api.dto.ShortenUrlResponse;
import snowf.urls.api.entity.UrlEntity;
import snowf.urls.api.jpa.UrlJpaRepository;
import snowf.urls.api.service.UrlCacheService;
import snowf.urls.api.service.UrlShortenerService;
import snowf.urls.api.service.ValidationService;
import snowf.urls.api.utils.Base62Util;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Value("${app.public-api-url}")
    private String publicApiUrl;

    private static final String REQ_ID_HEADER = "X-Request-ID";

    private static final Logger log = LogManager.getLogger(UrlShortenerServiceImpl.class);

    private final ValidationService validationService;
    private final UrlJpaRepository repository;
    private final UrlCacheService cacheService;
    private final HttpServletRequest httpServletRequest;

    public UrlShortenerServiceImpl(
            ValidationService validationService,
            UrlJpaRepository repository,
            UrlCacheService cacheService,
            HttpServletRequest httpServletRequest) {
        this.validationService = validationService;
        this.repository = repository;
        this.cacheService = cacheService;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public ShortenUrlResponse shortenUrl(ShortenUrlRequest request) {
        validationService.validate(request);

        Optional<UrlEntity> existing = repository.findByLongUrl(request.getLongUrl());

        if (existing.isPresent()) {
            if (existing.get().getAlias() != null) {
                cacheService.put(existing.get().getAlias(), request.getLongUrl());
                return toShortenUrlResponse(existing.get().getAlias());
            }

            String shortUrl = Base62Util.encode(BigInteger.valueOf(existing.get().getId()));
            cacheService.put(shortUrl, request.getLongUrl());

            return toShortenUrlResponse(shortUrl);
        }

        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setLongUrl(request.getLongUrl());

        if (request.getAlias() != null) {
            urlEntity.setAlias(request.getAlias());
        }

        repository.save(urlEntity);
        log.info("URL shortened successfully with request id : {}",
                httpServletRequest.getHeader(REQ_ID_HEADER));

        String shortUrl = (request.getAlias() != null) ?
                request.getAlias() :
                Base62Util.encode(BigInteger.valueOf(urlEntity.getId()));

        cacheService.put(shortUrl, request.getLongUrl());

        return toShortenUrlResponse(shortUrl);
    }

    private ShortenUrlResponse toShortenUrlResponse(String shortUrl) {
        String uri = UriComponentsBuilder
                .fromUriString(publicApiUrl)
                .path("/{shortUrl}")
                .buildAndExpand(shortUrl)
                .toUriString();

        return ShortenUrlResponse.builder()
                .url(uri)
                .shortUrl(shortUrl)
                .build();
    }

    @Override
    public String retrieveUrl(RedirectUrlRequest request) {
        validationService.validate(request);

        String url  = request.getUrl();
        String cache = cacheService.get(url);

        if (cache != null) {
            return cache;
        }

        Optional<UrlEntity> entity = repository.findByAlias(url);

        if (entity.isEmpty()) {
            try {
                String cleanedUrl = url.replaceAll("[^a-zA-Z0-9]", "");
                if (cleanedUrl.length() <= 10) {
                    entity = repository.findById(String.valueOf(Base62Util.decode(cleanedUrl)));
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        if (entity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL Not Found");
        }

        cacheService.put(request.getUrl(), entity.get().getLongUrl());

        return entity.get().getLongUrl();
    }
}
