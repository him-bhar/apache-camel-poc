package com.himanshu.poc.camel.pinger.producer;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingerProducer extends DefaultProducer {
  private static Logger logger = LoggerFactory.getLogger(PingerProducer.class);

  public PingerProducer(Endpoint endpoint) {
    super(endpoint);
  }

  @Override
  public void process(Exchange exchange) throws Exception {
    logger.info("Received message on the route : {}", exchange.getIn().getBody());
    //NOOP do any action you want to process this message.
  }
}
