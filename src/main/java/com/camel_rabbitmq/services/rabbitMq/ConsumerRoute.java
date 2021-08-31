package com.camel_rabbitmq.services.rabbitMq;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRoute extends RouteBuilder {

    @Override
    public void configure() {

        try {
            from("rabbitmq:testExchange?" +
                    "queue=testQueue&" +
                    "autoDelete=false&" +
                    "connectionFactory=#rabbitConnectionFactory&" +
                    "threadPoolSize=3000&" +
                    "concurrentConsumers=1900")
                    .routeId("RabbitMqConsumer")
                    .log("Get sms from queue with body: ${body}")
                    .to("smpp://smppclient@localhost:2777?" +
                            "enquireLinkTimer=3000&" +
                            "transactionTimer=5000&" +
                            "systemType=producer")
                    .log("Sent sms successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
