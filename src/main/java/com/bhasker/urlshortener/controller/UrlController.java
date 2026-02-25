package com.bhasker.urlshortener.controller;

import com.bhasker.urlshortener.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    // POST: /shorten?url=https://google.com
    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shorten(@RequestParam String url) {
        String code = service.shortenUrl(url);

        Map<String, String> res = new HashMap<>();
        res.put("shortUrl", "https://url-shortener-springboot-1.onrender.com/" + code);

        return ResponseEntity.ok(res);
    }

    // GET: /abc123 → redirect
    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {
        return service.getOriginalUrl(code)
                .map(original -> {
                    URI uri = URI.create(original);
                    return ResponseEntity.status(302)
                            .location(uri)
                            .build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}