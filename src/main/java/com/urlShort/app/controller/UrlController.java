package com.urlShort.app.controller;

import com.urlShort.app.entity.UrlMapping;
import com.urlShort.app.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;


    @PostMapping("/shorten")
    public String shorten(@RequestParam String url) {
        return "http://localhost:8080/" + urlService.createShortUrl(url);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {

        String longUrl = urlService.getLongUrl(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }
}
