package snowf.urls.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;
import snowf.urls.api.dto.RedirectUrlRequest;
import snowf.urls.api.dto.ShortenUrlRequest;
import snowf.urls.api.dto.ShortenUrlResponse;
import snowf.urls.api.dto.WebResponse;
import snowf.urls.api.service.UrlShortenerService;

import java.time.Instant;

@Controller
public class UrlShortenerController {

    private static final String REQ_ID_HEADER = "X-Request-ID";

    private final UrlShortenerService service;

    public UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping(
            path = "/api/shorten",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<ShortenUrlResponse>> shortenUrlEndpoint(
            @RequestBody ShortenUrlRequest request,
            HttpServletRequest httpServlet) {
        String requestId = httpServlet.getHeader(REQ_ID_HEADER);
        ShortenUrlResponse response = service.shortenUrl(request);

        WebResponse<ShortenUrlResponse> apiResponse = WebResponse.<ShortenUrlResponse>builder()
                .header(WebResponse.MessageHeader.builder()
                        .requestId(requestId)
                        .timestamp(Instant.now().toString())
                        .message("Success shortening an URL")
                        .path(httpServlet.getRequestURI())
                        .build())
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping(
            path = "/{shortUrl}"
    )
    public RedirectView redirectUrlEndpoint(
            @PathVariable String shortUrl) {

        RedirectUrlRequest urlRequest = new RedirectUrlRequest(shortUrl);

        try {
            return new RedirectView(service.retrieveUrl(urlRequest));
        } catch (ResponseStatusException e) {
            return new RedirectView("http://localhost:3000/not-found");
        }
    }



}
