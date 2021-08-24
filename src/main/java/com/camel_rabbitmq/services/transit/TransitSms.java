package com.camel_rabbitmq.services.transit;

import com.camel_rabbitmq.models.FacebookSms;
import com.cloudhopper.smpp.pdu.SubmitSm;
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

    public void addSmsToQueue(SubmitSm submitSm) {
        try {
            producerTemplate.asyncSendBody(producerTemplate.getDefaultEndpoint(), submitSm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
