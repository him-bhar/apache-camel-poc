package com.himanshu.poc;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.io.FileUtils;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        //Main main = new Main();
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(/*main.addRouteBuilder(*/new RouteBuilder() {
          
          @Override
          public void configure() throws Exception {
            from("file:C:/himanshu_work/sources/github/apache-camel-poc/camel-java-sample/src/data?noop=true")
            .bean(TestBeanClass.class, "invoke(${body})")
            .to("direct:choiceroute");
            /*.choice()
                .when(xpath("/person/city = 'London'"))
                    .process(new Processor() {
                      
                      @Override
                      public void process(Exchange arg0) throws Exception {
                        Message m = arg0.getIn();
                        File f = ((GenericFile<File>)m.getBody()).getFile();
                        arg0.getOut().setBody(FileUtils.readFileToString(f, Charset.forName("UTF-8")).concat("\n Manipulated in processor"));
                        arg0.setProperty("dummyext", "hello");
                        arg0.setProperty("fileName", f.getName());
                      }
                    })
                    .log("UK message")
                    //.to("file:target/messages/uk?fileName=${file:name.noext}'.'${exchangeProperty.dummyext}") //Applicable when File object is the OUT
                    .to("file:target/messages/uk?fileName=${exchangeProperty.fileName}.${exchangeProperty.dummyext}")
                    
                .otherwise()
                    .log("Other message")
                    .to("file:target/messages/others?fileName=${file:name.noext}");*/
          }
        });
        context.addRoutes(/*main.addRouteBuilder(*/new RouteBuilder() {
          
          @Override
          public void configure() throws Exception {
            from("direct:choiceroute")
            .choice()
                .when(xpath("/person/city = 'London'"))
                    .process(new Processor() {
                      
                      @Override
                      public void process(Exchange arg0) throws Exception {
                        Message m = arg0.getIn();
                        File f = ((GenericFile<File>)m.getBody()).getFile();
                        arg0.getOut().setBody(FileUtils.readFileToString(f, Charset.forName("UTF-8")).concat("\n Manipulated in processor"));
                        arg0.setProperty("dummyext", "hello");
                        arg0.setProperty("fileName", f.getName());
                      }
                    })
                    .log("UK message")
                    //.to("file:target/messages/uk?fileName=${file:name.noext}'.'${exchangeProperty.dummyext}") //Applicable when File object is the OUT
                    .to("file:target/messages/uk?fileName=${exchangeProperty.fileName}.${exchangeProperty.dummyext}")
                    
                .otherwise()
                    .log("Other message")
                    .to("file:target/messages/others?fileName=${file:name.noext}");
          }
        });
        context.start();
        Thread.currentThread().join();
        //main.run(args);
        
    }

}

