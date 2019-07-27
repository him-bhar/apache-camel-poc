package com.himanshu.camel.component.pinger;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

public class PingerComponent extends DefaultComponent {
  @Override
  protected Endpoint createEndpoint(String protocol, String uri,  Map<String, Object> uriProperties) throws Exception {
    PingerEndpoint pingerEndpoint = new PingerEndpoint("pinger", this);
    pingerEndpoint.setGreeting(uri);
    return pingerEndpoint;

  }
}
