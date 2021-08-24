package com.camel_rabbitmq.services.transit.impl;

import com.camel_rabbitmq.models.FacebookSms;
import com.camel_rabbitmq.services.transit.ITransitSms;
import com.cloudhopper.smpp.pdu.SubmitSm;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransitSms implements ITransitSms {

    @Produce(value = "direct:startQueuePoint")
    private final ProducerTemplate producerTemplate;

    @Autowired
    public TransitSms(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

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
