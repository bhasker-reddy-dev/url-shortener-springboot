package com.bhasker.urlshortener.controller;

import com.bhasker.urlshortener.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000") 
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }
    @GetMapping("/")
public String home() {
    return "URL Shortener API is running! Use /shorten to create short URLs.";
}

  @PostMapping("/shorten")
public ResponseEntity<Map<String, String>> shorten(@RequestParam String url) {
    String shortCode = service.shortenUrl(url);
    Map<String, String> result = new HashMap<>();
    result.put("shortUrl", "https://url-shortener-springboot-1.onrender.com/" + shortCode);
    return ResponseEntity.ok(result);
}

    @SuppressWarnings({ "unused", "null" })
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        return service.getOriginalUrl(shortCode)
                .map(originalUrl ->
                        ResponseEntity
                                .status(302)
                                .location(URI.create(originalUrl))
                                .<Void>build()
                )
                .orElse(ResponseEntity.<Void>notFound().build());
    }
}