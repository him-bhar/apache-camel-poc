# Camel Java Router Project

To build this project use

    mvn install

## What it covers
### Simple route building
Have a look at MainApp, MyRouteBuilder

### Camel + JMS (Both In-Mem and ActiveMQ broker)
1. Have a look at JmsCommsTest (not camel, just demonstrating how Embedded broker works)
1. JmsRouteTest, it tests both the aspect i.e. publishing to JMS server and reading from JMS server.