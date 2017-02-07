package com.himanshu.poc.camel.routes;

import java.util.LinkedList;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;

import com.himanshu.poc.TestBeanClass;

public class RoutesContainer {
  
  private RouteBuilder routeBuilder = new RouteBuilder(null) {
    
    @Override
    public void configure() throws Exception {
      //NOOP
    }
  };
  
  private LinkedList<ProcessorDefinition> processorDefinitions = new LinkedList<>();
  
  public void createNewRoute(String uri) {
    //RouteDefinition routeDefinition = this.routeBuilder.from(uri).bean(TestBeanClass.class, "invoke(${body})");
    RouteDefinition routeDefinition = this.routeBuilder.from(uri);
    routeDefinition.choice()
      .when((exchange) -> {return exchange.getIn().getBody(String.class).equalsIgnoreCase("Hello Himanshu");}).bean(TestBeanClass.class, "hello(${body}, ${headers})")
      .when((exchange) -> {return exchange.getIn().getBody(String.class).equalsIgnoreCase("Bye Himanshu");}).bean(TestBeanClass.class, "bye(${body}, ${headers})")
      .otherwise().bean(TestBeanClass.class, "timepass(${body}, ${headers})")
      .endChoice()
      ;
    routeDefinition.end();
    processorDefinitions.add(routeDefinition);
  }
  
  public RouteBuilder getRouteBuilder() {
    return routeBuilder;
  }

}
