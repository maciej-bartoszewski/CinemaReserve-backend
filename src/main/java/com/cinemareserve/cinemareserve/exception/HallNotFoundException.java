package com.cinemareserve.cinemareserve.exception;

public class HallNotFoundException extends RuntimeException {
  public HallNotFoundException(String message) {
    super(message);
  }
}