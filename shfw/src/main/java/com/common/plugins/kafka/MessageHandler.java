package com.common.plugins.kafka;

public interface MessageHandler {
  void handle(String msg);
}
