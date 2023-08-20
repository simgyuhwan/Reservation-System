package com.sim.reservation.data.reservation.provider;

import com.sim.reservation.data.reservation.error.ErrorMessage;
import java.util.concurrent.Callable;
import org.redisson.api.RLock;

/**
 * 동시성 이슈 제어를 위한 잠금 제공 인터페이스
 */
public interface LockProvider {

  /**
   * 키를 사용하여 잠금을 얻는다.
   */
  RLock getLock(String key);

  /**
   * 키에 해당하는 자원에 대한 접근 권할을 얻는 메서드
   *
   * @param key       얻으려는 키
   * @param waitTime  접근 권한을 얻을 최대 시간
   * @param leaseTime 접근 권한을 얻은 후 최대 점유 시간
   * @param unit      waitTime, leaseTime 의 시간 단위
   * @return Lock 이 다른 스레드가 점유하면 false, 아니면 true
   * @throws InterruptedException
   */
  boolean tryLock(String key)
      throws InterruptedException;

  /**
   * 키에 대한 락을 얻을 떄까지 block 대기한다.
   *
   * @param key
   */
  void lock(String key);

  /**
   * 키에 대한 락을 해제한다.
   *
   * @param key
   */
  void unlock(String key);

  <T> T tryLockAndExecute(String key, ErrorMessage errorMessage, Callable<T> task)
      throws InterruptedException;

}
