package com.himanshu.poc.camel.pinger.endpoint;

import com.himanshu.poc.camel.pinger.consumer.PingerConsumer;
import com.himanshu.poc.camel.pinger.producer.PingerProducer;
import org.apache.camel.*;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;

@UriEndpoint(title = "Pinger endpoint", scheme = "pinger", syntax = "pinger:greeting")
public class PingerEndpoint extends DefaultEndpoint implements MultipleConsumersSupport {
  @UriPath(description = "greeting to send in ping message")
  @Metadata(required = "true")
  private String greeting;

  @UriParam(name = "pingInterval", description = "Time intervals in seconds between each ping", defaultValue = "5")
  private long pingInterval;

  @UriParam(name = "pings", description = "Total pings to send", defaultValue = "3")
  private long pings;

  public PingerEndpoint(String endpointUri, Component component) {
    super(endpointUri, component);
  }

  @Override
  public Producer createProducer() throws Exception {
    return new PingerProducer(this);
  }

  @Override
  public Consumer createConsumer(Processor processor) throws Exception {
    return new PingerConsumer(this, processor);
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  public String getGreeting() {
    return greeting;
  }

  public void setGreeting(String greeting) {
    this.greeting = greeting;
  }

  public long getPingInterval() {
    return pingInterval;
  }

  public void setPingInterval(long pingInterval) {
    this.pingInterval = pingInterval;
  }

  public long getPings() {
    return pings;
  }

  public void setPings(long pings) {
    this.pings = pings;
  }

  @Override
  public boolean isMultipleConsumersSupported() {
    return true;
  }
}
