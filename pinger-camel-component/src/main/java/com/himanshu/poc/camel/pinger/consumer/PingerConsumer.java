package com.himanshu.poc.camel.pinger.consumer;

import com.himanshu.poc.camel.pinger.endpoint.PingerEndpoint;
import com.himanshu.poc.camel.pinger.producer.PingerProducer;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @see {https://stackoverflow.com/questions/35480794/apache-camel-creating-consumer-component}
 * @see {http://oscerd.github.io/oscerd.github.io//2016/07/06/contributing-camel-components/}
 */

public class PingerConsumer extends DefaultConsumer {
  private static Logger logger = LoggerFactory.getLogger(PingerConsumer.class);

  private AtomicInteger atomicInteger = new AtomicInteger(0);

  private PingerEndpoint pingerEndpoint;
  private ScheduledThreadPoolExecutor pingerExecutor;

  public PingerConsumer(Endpoint endpoint, Processor processor) {
    super(endpoint, processor);
    this.pingerEndpoint = (PingerEndpoint) endpoint;
    this.pingerExecutor = new ScheduledThreadPoolExecutor(1);
  }

  @Override
  protected void doStart() throws Exception {
    super.doStart();
    this.pingerExecutor.scheduleAtFixedRate(() -> {
      int i = atomicInteger.incrementAndGet();
      if (i>this.pingerEndpoint.getPings()) {
        try {
          this.doStop();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      logger.info("Sending message to exchange: {} ", i);
      Exchange exchange = pingerEndpoint.createExchange(ExchangePattern.InOnly);
      exchange.setFromEndpoint(this.pingerEndpoint);
      exchange.getIn().setBody(this.pingerEndpoint.getGreeting()+" - "+i);
      try {
        getProcessor().process(exchange);
      } catch (Exception e) {
        logger.error("Error sending message on exchange", e);
        throw new RuntimeException(e);
      }
    }, 10l, pingerEndpoint.getPingInterval(), TimeUnit.SECONDS);
  }

  @Override
  protected void doStop() throws Exception {
    super.doStop();
    this.pingerExecutor.shutdown();
  }

}
