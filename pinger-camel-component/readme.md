A custom camel component has been developed for demonstrating how it works.

**Component name**: pinger

This component acts both like a consumer as well as a producer.

#Pinger (pinger:)

##As a consumer
When a component acts like a consumer, it means that component will be defined for use at the **from** level of the route.
```
>>>> from("pinger://ping?pings=10&pingInterval=1")
  .routeId("test-route-1")
  .to("log:com.mycompany.order?level=INFO")
  .to("mock:result");
```
Have a look at test class: ```PingerConsumerTest```

##As a producer
When a component acts like a producer, it means that component will be defined for use at the **to** level of the route.
```
from("pinger://ping?pings=10&pingInterval=1").routeId("test-route-1")
  .to("log:com.mycompany.order?level=INFO")
  >>>> .to("pinger:ping")
  .to("mock:result");
```
Have a look at test class: ```PingerProducerTest```