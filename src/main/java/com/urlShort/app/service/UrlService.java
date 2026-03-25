package com.urlShort.app.service;

import com.urlShort.app.entity.UrlMapping;
import com.urlShort.app.repository.UrlRepository;
import com.urlShort.app.utils.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final StringRedisTemplate redisTemplate;

    public String createShortUrl(String longUrl) {

        UrlMapping mapping = new UrlMapping();
        mapping.setLongUrl(longUrl);
        mapping.setCreatedAt(LocalDateTime.now());
        mapping.setExpiryTime(LocalDateTime.now().plusDays(1));

        mapping = urlRepository.save(mapping);

        String shortCode = Base62Encoder.encode(mapping.getId());

        mapping.setShortCode(shortCode);
        urlRepository.save(mapping);

        return shortCode;
    }

    public String getLongUrl(String shortCode) {

        redisTemplate.opsForValue().increment("clicks:" + shortCode);
        String cachedUrl = redisTemplate.opsForValue().get("url:" + shortCode);

        if (cachedUrl != null) {
            return cachedUrl;
        }

        UrlMapping mapping = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        if (mapping.getExpiryTime() != null &&
                mapping.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("URL expired");
        }

        mapping.setClickCount(mapping.getClickCount() + 1);
        urlRepository.save(mapping);

        redisTemplate.opsForValue().set(
                "url:" + shortCode,
                mapping.getLongUrl(),
                1, TimeUnit.DAYS
        );

        return mapping.getLongUrl();
    }
}
