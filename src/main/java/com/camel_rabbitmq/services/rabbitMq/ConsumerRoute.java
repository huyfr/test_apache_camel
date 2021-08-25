package com.camel_rabbitmq.services.rabbitMq;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRoute extends RouteBuilder {

    @Override
    public void configure() {

        try {
            from("rabbitmq:testExchange?queue=testQueue&autoDelete=false&connectionFactory=#rabbitConnectionFactory")
                    .routeId("RabbitMqConsumer")
                    .log("Get sms from queue with body: ${body}")
                    .to("smpp://smppclient@localhost:2777?password=password&enquireLinkTimer=3000&transactionTimer=5000&systemType=producer")
                    .log("Sent sms successful!")
                    .end();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
