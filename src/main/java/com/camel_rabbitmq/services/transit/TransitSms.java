package com.camel_rabbitmq.services.transit;

import com.camel_rabbitmq.models.FacebookSms;
import com.cloudhopper.smpp.pdu.PduRequest;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransitSms {

    @Produce(value = "direct:startQueuePoint")
    private ProducerTemplate producerTemplate;


    public void testAddSmsToQueue(FacebookSms facebookSms) {
        try {
            producerTemplate.asyncSendBody(producerTemplate.getDefaultEndpoint(), facebookSms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSmsToQueue(PduRequest pduRequest) {
        try {
            producerTemplate.asyncSendBody(producerTemplate.getDefaultEndpoint(), pduRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
