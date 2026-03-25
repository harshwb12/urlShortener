package com.urlShort.app.scheduler;

import com.urlShort.app.entity.UrlMapping;
import com.urlShort.app.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ClickSyncScheduler {

    private final StringRedisTemplate redisTemplate;
    private final UrlRepository urlRepository;

    @Scheduled(fixedRate = 60000) // every 1 minute
    public void syncClickCounts() {

        Set<String> keys = redisTemplate.keys("clicks:*");

        if (keys == null || keys.isEmpty()) return;

        for (String key : keys) {

            String shortCode = key.replace("clicks:", "");
            String value = redisTemplate.opsForValue().get(key);

            if (value == null) continue;

            int clicks = Integer.parseInt(value);

            UrlMapping mapping = urlRepository.findByShortCode(shortCode)
                    .orElse(null);

            if (mapping != null) {
                mapping.setClickCount(mapping.getClickCount() + clicks);
                urlRepository.save(mapping);
            }

            // reset counter
            redisTemplate.delete(key);
        }
    }
}
