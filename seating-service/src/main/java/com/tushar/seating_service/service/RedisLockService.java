package com.tushar.seating_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisLockService {
    private final RedisTemplate<String, String> redisTemplate;

    private static final String LOCK_KEY_PREFIX = "lock:seat:";
    private static final long LOCK_TIMEOUT_SECONDS = 600;

    public boolean lockSeat(Integer flightId, String seatNumber, Integer userId) {
        String lockKey = LOCK_KEY_PREFIX + flightId + ":" + seatNumber;

        Boolean acquired = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, String.valueOf(userId), LOCK_TIMEOUT_SECONDS, TimeUnit.SECONDS);

        if (Boolean.TRUE.equals(acquired)) {
            log.debug("Lock acquired for seat: {} by user: {} for 10 minutes", seatNumber, userId);
            return true;
        }

        log.debug("Lock failed for seat: {} - already held", seatNumber);
        return false;
    }

    public void unlockSeat(Integer flightId, String seatNumber) {
        String lockKey = LOCK_KEY_PREFIX + flightId + ":" + seatNumber;
        redisTemplate.delete(lockKey);
        log.debug("Lock released for seat: {}", seatNumber);
    }

    public boolean isSeatLocked(Integer flightId, String seatNumber) {
        String lockKey = LOCK_KEY_PREFIX + flightId + ":" + seatNumber;
        return Boolean.TRUE.equals(redisTemplate.hasKey(lockKey));
    }

    public Integer getLockOwner(Integer flightId, String seatNumber) {
        String lockKey = LOCK_KEY_PREFIX + flightId + ":" + seatNumber;
        String owner = redisTemplate.opsForValue().get(lockKey);
        return owner != null ? Integer.parseInt(owner) : null;
    }
}
