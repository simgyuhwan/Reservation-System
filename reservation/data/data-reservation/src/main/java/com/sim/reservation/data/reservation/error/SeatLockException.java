package com.sim.reservation.data.reservation.error;

public class SeatLockException extends RuntimeException {

  private ErrorMessage errorMessage;

  public SeatLockException() {
    super();
  }

  public SeatLockException(ErrorMessage message) {
    super(message.getMessage());
    this.errorMessage = message;
  }

}
