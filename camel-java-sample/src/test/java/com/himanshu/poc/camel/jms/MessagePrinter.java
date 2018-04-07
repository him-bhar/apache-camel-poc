package com.himanshu.poc.camel.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessagePrinter {
  private static Logger logger = LoggerFactory.getLogger(MessagePrinter.class);

  public void printMessage(String message) {
    logger.info("Message is: {}", message);
  }

}
