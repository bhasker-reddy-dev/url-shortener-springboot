package com.bhasker.urlshortener.service;

import com.bhasker.urlshortener.model.UrlMapping;
import com.bhasker.urlshortener.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {

    private final UrlMappingRepository repository;

    public UrlService(UrlMappingRepository repository) {
        this.repository = repository;
    }

    public String shortenUrl(String originalUrl) {

        String shortCode = generateShortCode();

        UrlMapping mapping = new UrlMapping(originalUrl, shortCode);
        repository.save(mapping);

        return shortCode;
    }

    public Optional<String> getOriginalUrl(String shortCode) {
        return repository.findByShortCode(shortCode)
                .map(UrlMapping::getOriginalUrl);
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}