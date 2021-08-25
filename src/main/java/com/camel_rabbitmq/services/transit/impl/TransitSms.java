package com.camel_rabbitmq.services.transit.impl;

import com.camel_rabbitmq.models.FacebookSms;
import com.camel_rabbitmq.services.transit.ITransitSms;
import com.cloudhopper.smpp.pdu.SubmitSm;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.camel_rabbitmq.ulti.CamelSmppValueHeader.*;


@Service
public class TransitSms implements ITransitSms {
    private static final Logger logger = LoggerFactory.getLogger(TransitSms.class);
    HashMap<String, Object> smsHeaders = new HashMap<>();

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
            long endTime = System.currentTimeMillis();
            setSmsHeaders(submitSm);
            producerTemplate.sendBodyAndHeaders(producerTemplate.getDefaultEndpoint(), new String (submitSm.getShortMessage()), smsHeaders);
            logger.info("End time: {}", endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSmsHeaders(SubmitSm submitSm) {
        try {
            smsHeaders.put("CamelSmppDestAddr", submitSm.getDestAddress().getAddress());
            smsHeaders.put("CamelSmppDestAddrTon", SMPP_TON_ALNUM);
            smsHeaders.put("CamelSmppDestAddrNpi", SMPP_NPI_UNK);
            smsHeaders.put("CamelSmppSourceAddr", submitSm.getSourceAddress().getAddress());
            smsHeaders.put("CamelSmppSourceAddrTon", SMPP_TON_INTL);
            smsHeaders.put("CamelSmppSourceAddrNpi", SMPP_NPI_ISDN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
