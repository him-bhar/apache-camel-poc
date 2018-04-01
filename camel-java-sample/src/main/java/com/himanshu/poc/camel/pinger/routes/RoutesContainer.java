package com.himanshu.poc.camel.pinger.routes;

import com.himanshu.poc.TestBeanClass;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class RoutesContainer {

  private static Logger logger = LoggerFactory.getLogger(RoutesContainer.class);
  
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
      .when((exchange) -> exchange.getIn().getBody(String.class).equalsIgnoreCase("Hello Himanshu")).to("direct:hello")
      .when((exchange) -> exchange.getIn().getBody(String.class).equalsIgnoreCase("Bye Himanshu")).to("direct:bye")
      .otherwise().to("direct:timepass")
      .endChoice()
      ;
    routeDefinition.end();
    processorDefinitions.add(routeDefinition);
  }
  
  public void createParallelRoute(String uri) {
    RouteDefinition routeDefinition = this.routeBuilder.from(uri);
    routeDefinition.multicast((oldExchange, newExchange) -> {
      logger.info("newExchange in: "+newExchange.getIn().getBody());
      logger.info("newExchange out: "+newExchange.getOut().getBody());
      newExchange.getOut().setBody(newExchange.getIn().getBody());
      if (oldExchange != null) {
        logger.info("oldExchange in: "+oldExchange.getIn().getBody());
        logger.info("oldExchange out: "+oldExchange.getOut().getBody());
      }
      return newExchange;
    }).parallelProcessing().to("direct:hello", "direct:bye", "direct:timepass");
    /*.parallelProcessing().aggregate(new AggregationStrategy() {
      
      @Override
      public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        logger.info("newExchange in: "+newExchange.getIn().getBody());
        logger.info("newExchange out: "+newExchange.getOut().getBody());
        logger.info("oldExchange in: "+oldExchange.getIn().getBody());
        logger.info("oldExchange out: "+oldExchange.getOut().getBody());
        return newExchange;
      }
    });*/
    routeDefinition.end();
    processorDefinitions.add(routeDefinition);
  }
  
  public void createHelloRoute() {
    RouteDefinition routeDefinition = this.routeBuilder.from("direct:hello");
    routeDefinition.bean(TestBeanClass.class, "hello(${body}, ${headers})");
    routeDefinition.end();
  }
  
  public void createByeRoute() {
    RouteDefinition routeDefinition = this.routeBuilder.from("direct:bye");
    routeDefinition.bean(TestBeanClass.class, "bye(${body}, ${headers})");
    routeDefinition.end();
  }
  
  public void createTimePassRoute() {
    RouteDefinition routeDefinition = this.routeBuilder.from("direct:timepass");
    routeDefinition.bean(TestBeanClass.class, "timepass(${body}, ${headers})");
    routeDefinition.end();
  }
  
  public RouteBuilder getRouteBuilder() {
    return routeBuilder;
  }

}
