package com.himanshu.camel.component.pinger.component;

import com.himanshu.camel.component.pinger.PingerComponent;
import org.apache.camel.FailedToCreateRouteException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class PingerConsumerTest extends CamelTestSupport {

  @Test
  public void pingerMultiConsumerTest() throws Exception {
    context().addComponent("pinger", new PingerComponent());

    RouteBuilder routeBuilder = new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("pinger://ping?pings=10&pingInterval=1").routeId("test-route-1")
                .to("log:com.mycompany.order?level=INFO")
                .to("mock:result");
      }
    };

    RouteBuilder routeBuilder2 = new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("pinger://hello?pings=10&pingInterval=1").routeId("test-route-2")
                .to("log:com.mycompany.newOrder?level=INFO")
                .to("mock:result2");
      }
    };
    context().addRoutes(routeBuilder);
    context().addRoutes(routeBuilder2);

    MockEndpoint mock = getMockEndpoint("mock:result");
    MockEndpoint mock2 = getMockEndpoint("mock:result2");

    mock.expectedMinimumMessageCount(10);
    mock2.expectedMinimumMessageCount(10);

    assertMockEndpointsSatisfied(40, TimeUnit.SECONDS);
  }

  @Test
  public void pingerConsumerTest() throws Exception {
    context().addComponent("pinger", new PingerComponent());

    RouteBuilder routeBuilder = new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("pinger://ping?pings=10&pingInterval=1").routeId("test-route-1")
                .to("log:com.mycompany.order?level=INFO")
                .to("mock:result");
      }
    };

    context().addRoutes(routeBuilder);

    MockEndpoint mock = getMockEndpoint("mock:result");

    mock.expectedMinimumMessageCount(10);
    assertMockEndpointsSatisfied(40, TimeUnit.SECONDS);
  }

  @Test(expected = FailedToCreateRouteException.class)
  public void shouldErrorWhenComponentNotRegistered() throws Exception {

    RouteBuilder routeBuilder = new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("pinger://ping?pings=10&pingInterval=1").routeId("test-route-1")
                .to("log:com.mycompany.order?level=INFO")
                .to("mock:result");
      }
    };

    context().addRoutes(routeBuilder);

    MockEndpoint mock = getMockEndpoint("mock:result");

    mock.expectedMinimumMessageCount(10);
    assertMockEndpointsSatisfied(40, TimeUnit.SECONDS);
  }

}
