package com.himanshu.poc.camel.jms;

import com.himanshu.poc.camel.pinger.component.PingerComponent;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.impl.PropertyPlaceholderDelegateRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import java.util.concurrent.TimeUnit;

public class JmsRouteTest extends CamelTestSupport {

  private static ConnectionFactory connectionFactory;
  private Connection connection;
  private Session session;
  private static Logger logger = LoggerFactory.getLogger(JmsRouteTest.class);

  @BeforeClass
  public static void setupJms() {
    //In-Mem JMS
    connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
    //Actual deployed ActiveMq
    //connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
  }

  @Before
  public void setupJmsConnection() throws JMSException {
    connection = connectionFactory.createConnection();
    connection.start();
    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
  }

  @After
  public void cleanupJmsConnection() throws JMSException {
    session.close();
    connection.stop();
  }

  @Test
  public void testSendAndConsumeRoute() throws Exception {
    JndiRegistry jndiContext = (JndiRegistry) ((PropertyPlaceholderDelegateRegistry)context().getRegistry()).getRegistry();
    jndiContext.bind("custom-connection-factory", connectionFactory);
    jndiContext.bind("messagePrinter", new MessagePrinter());

    context().addComponent("pinger", new PingerComponent());

    MockEndpoint mock = getMockEndpoint("mock:result");

    RouteBuilder sendToJmsRoute = new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("pinger://ping?pings=10&pingInterval=1")
                .process(exchange -> logger.info("Processing message: {}", exchange.getIn().getBody()))
                .to("jms:queue:temp_queue?connectionFactory=#custom-connection-factory")
                .to("bean:messagePrinter?method=printMessage(${body})")
                .to("mock:result");
      }
    };

    context().addRoutes(sendToJmsRoute);
    mock.expectedMinimumMessageCount(10);


    MockEndpoint mock2 = getMockEndpoint("mock:result2");

    RouteBuilder consumeFromJmsRoute = new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("jms:queue:temp_queue?connectionFactory=#custom-connection-factory")
                .to("bean:messagePrinter?method=printMessage(${body})")
                .to("mock:result2");
      }
    };

    context().addRoutes(consumeFromJmsRoute);
    mock2.expectedMinimumMessageCount(10);

    assertMockEndpointsSatisfied(40, TimeUnit.SECONDS);
  }
}
