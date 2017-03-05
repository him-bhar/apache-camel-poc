package com.himanshu.poc;

import java.io.File;
import java.util.Map;

public class TestBeanClass {
  public void invoke(File f) {
    System.out.println("Inside test bean class invoke file method");
    System.out.println(f);
  }
  
  public String invoke(String s) {
    System.out.println("Inside test bean class invoke string method");
    System.out.println(s);
    return "Called invoke";
  }
  
  public String playWithMe(String s) {
    System.out.println("Inside test bean class playWithMe string method");
    System.out.println(s);
    return "playWithMe";
  }
  
  public String hello(String s, Map<String, Object> headerMap) {
    System.out.println("Just said hello");
    System.out.println(headerMap);
    return "hello";
  }
  
  public String bye(String s, Map<String, Object> headerMap) {
    System.out.println("Just said bye");
    System.out.println(headerMap);
    return "bye";
  }
  
  public String timepass(String s, Map<String, Object> headerMap) {
    System.out.println("Timepassing!");
    System.out.println(headerMap);
    return "timepass";
  }
}
