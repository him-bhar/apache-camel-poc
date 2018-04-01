package com.himanshu.poc.camel.pinger.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {
  public static void main(String[] args) throws Exception {
    RoutesContainer rc = new RoutesContainer();
    rc.createNewRoute("direct://start");
    rc.createHelloRoute();
    rc.createByeRoute();
    rc.createTimePassRoute();
    rc.createParallelRoute("direct://parallel");
    CamelContext context = new DefaultCamelContext();
    context.addRoutes(rc.getRouteBuilder());
    context.start();
    
    ProducerTemplate template = context.createProducerTemplate();
    template.sendBody("direct://start", "Hello Himanshu");
    template.sendBody("direct://start", "Himanshu is having fun");
    template.sendBody("direct://start", "Bye Himanshu");
    //Thread.currentThread().join();
    Thread.sleep(5000l);
    template.sendBody("direct://parallel", "Bye Himanshu");
    Thread.sleep(10000l);
  }
}
