package com.himanshu.poc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TestBeanClass {
  private static Logger logger = LoggerFactory.getLogger(TestBeanClass.class);
  /*public void invoke(File f) {
    logger.info("Inside test bean class invoke file method");
    logger.info(f);
  }*/
  
  public String invoke(String s) {
    logger.info("Inside test bean class invoke string method");
    logger.info(s);
    logger.info("Called invoke");
    return s;
  }
  
  public String playWithMe(String s) {
    logger.info("Inside test bean class playWithMe string method");
    logger.info(s);
    return "playWithMe";
  }
  
  public String hello(String s, Map<String, Object> headerMap) {
    logger.info("Just said hello");
    logger.info(String.valueOf(headerMap));
    return "hello";
  }
  
  public String bye(String s, Map<String, Object> headerMap) {
    logger.info("Just said bye");
    logger.info(String.valueOf(headerMap));
    return "bye";
  }
  
  public String timepass(String s, Map<String, Object> headerMap) {
    logger.info("Timepassing!");
    logger.info(String.valueOf(headerMap));
    return "timepass";
  }
}
