package com.betterlxc;

/**
 * Created by LXC on 2017/5/10.
 */
public class SeedException extends RuntimeException {

  public SeedException() {
    super();
  }

  public SeedException(String message) {
    super(message);
  }

  public SeedException(String message, Throwable cause) {
    super(message, cause);
  }

  public SeedException(Throwable cause) {
    super(cause);
  }

}
