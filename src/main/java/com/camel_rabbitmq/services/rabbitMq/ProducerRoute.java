package com.camel_rabbitmq.services.rabbitMq;

import com.camel_rabbitmq.models.FacebookSms;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class ProducerRoute extends RouteBuilder {

    @Override
    public void configure() {

        try {
            JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(FacebookSms.class);

            from("direct:startQueuePoint")
                    .id("RabbitMqProducer")
                    .marshal(jacksonDataFormat)
                    .to("rabbitmq:testExchange?queue=testQueue&autoDelete=false&connectionFactory=#rabbitConnectionFactory")
                    .log("Message Sent!")
                    .end();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
