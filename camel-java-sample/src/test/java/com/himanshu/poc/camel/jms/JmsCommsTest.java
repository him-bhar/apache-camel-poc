package com.himanshu.poc.camel.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.*;

import javax.jms.*;

public class JmsCommsTest {
  private static ConnectionFactory connectionFactory;
  private Connection connection;
  private Session session;

  @BeforeClass
  public static void setupJms() {
    connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
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
  public void sendAndConsumeJmsMessage() throws JMSException {
    String message = "Hello world!";

    MessageProducer producer = session.createProducer(new ActiveMQQueue("temp_queue"));
    producer.send(session.createTextMessage(message));

    MessageConsumer consumer = session.createConsumer(new ActiveMQQueue("temp_queue"));
    Message rcvdMessage = consumer.receive(100l);
    Assert.assertNotNull(rcvdMessage);
    Assert.assertTrue(rcvdMessage instanceof TextMessage);
    Assert.assertEquals(message, ((TextMessage)rcvdMessage).getText());
    /*consumer.setMessageListener((jmsMessage -> {
      try {
        System.out.println(((TextMessage)jmsMessage).getText());
      } catch (JMSException e) {
        e.printStackTrace();
      }
    }));*/
  }
}
