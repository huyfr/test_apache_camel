package com.camel_rabbitmq.services.rabbitMq;

import com.cloudhopper.smpp.pdu.SubmitSm;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

@Component
public class ProducerRoute extends RouteBuilder {

    @Override
    public void configure() {

        try {
            JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(SubmitSm.class);

            from("direct:startQueuePoint")
                    .id("RabbitMqProducer")
                    .marshal(jacksonDataFormat)
                    .to("rabbitmq:testExchange?queue=testQueue&autoDelete=false&connectionFactory=#rabbitConnectionFactory")
                    .log("Sms has entered queue!")
                    .end();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
