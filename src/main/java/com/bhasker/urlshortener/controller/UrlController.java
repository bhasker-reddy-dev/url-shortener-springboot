package com.bhasker.urlshortener.controller;

import com.bhasker.urlshortener.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestParam String url) {
        String shortCode = service.shortenUrl(url);
        return ResponseEntity.ok("http://localhost:8080/" + shortCode);
    }

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