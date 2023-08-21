package com.sim.reservation.data.reservation.provider;

import com.sim.reservation.data.reservation.error.ErrorMessage;
import com.sim.reservation.data.reservation.error.InternalException;
import com.sim.reservation.data.reservation.error.SeatLockException;
import java.util.concurrent.Callable;
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
  private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

  @Override
  public RLock getLock(String key) {
    return redissonClient.getLock(key);
  }

  @Override
  public boolean tryLock(String key)
      throws InterruptedException {
    RLock lock = getLock(key);
    return lock.tryLock(WAIT_TIME, LEASE_TIME, TIME_UNIT);
  }

  @Override
  public void lock(String key) {
    RLock lock = getLock(key);
    lock.lock();
  }

  @Override
  public void unlock(String key) {
    RLock lock = getLock(key);
    lock.unlock();
  }

  @Override
  public <T> T tryLockAndExecute(String key, ErrorMessage errorMessage, Callable<T> task)
      throws InterruptedException {
    RLock lock = getLock(key);
    try {
      if (lock.tryLock(WAIT_TIME, LEASE_TIME, TIME_UNIT)) {
        return task.call();
      } else {
        throw new SeatLockException(errorMessage);
      }
    } catch (Exception e) {
      throw new InternalException(e);
    } finally {
      lock.unlock();
    }
  }
}
