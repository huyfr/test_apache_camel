package com.camel_rabbitmq;

import com.cloudhopper.smpp.type.SmppChannelException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamelRabbitmqApplication {

    public static void main(String[] args) throws SmppChannelException {
        SpringApplication.run(CamelRabbitmqApplication.class, args);
    }

}
