package com.sim.reservation.data.reservation.provider;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisLockProvider implements LockProvider {

  private final RedissonClient redissonClient;
  private static final int WAIT_TIME = 1;
  private static final int LEASE_TIME = 2;
  private static final String PREFIX = "SEAT_LOCK: ";
  private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

  @Override
  public RLock getLock(String key) {
    return redissonClient.getLock(PREFIX + key);
  }

  @Override
  public boolean tryLock(String key)
      throws InterruptedException {
    RLock lock = getLock(PREFIX + key);
    return lock.tryLock(WAIT_TIME, LEASE_TIME, TIME_UNIT);
  }

  @Override
  public void lock(String key) {
    RLock lock = getLock(PREFIX + key);
    lock.lock();
  }

  @Override
  public void unlock(String key) {
    RLock lock = getLock(PREFIX + key);
    lock.unlock();
  }
}
