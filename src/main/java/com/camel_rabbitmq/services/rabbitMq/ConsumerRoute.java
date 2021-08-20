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
                    .log("Received body: ${body}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
