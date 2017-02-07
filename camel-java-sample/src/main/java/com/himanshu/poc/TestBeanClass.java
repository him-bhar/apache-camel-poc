package com.himanshu.poc;

import java.io.File;
import java.util.Map;

public class TestBeanClass {
  public void invoke(File f) {
    System.out.println("Inside test bean class invoke file method");
    System.out.println(f);
  }
  
  public void invoke(String s) {
    System.out.println("Inside test bean class invoke string method");
    System.out.println(s);
  }
  
  public void playWithMe(String s) {
    System.out.println("Inside test bean class playWithMe string method");
    System.out.println(s);
  }
  
  public void hello(String s, Map<String, Object> headerMap) {
    System.out.println("Just said hello");
    System.out.println(headerMap);
  }
  
  public void bye(String s, Map<String, Object> headerMap) {
    System.out.println("Just said bye");
    System.out.println(headerMap);
  }
  
  public void timepass(String s, Map<String, Object> headerMap) {
    System.out.println("Timepassing!");
    System.out.println(headerMap);
  }
}
