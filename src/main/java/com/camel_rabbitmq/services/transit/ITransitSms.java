package com.camel_rabbitmq.services.transit;

import com.camel_rabbitmq.models.FacebookSms;
import com.cloudhopper.smpp.pdu.SubmitSm;

public interface ITransitSms {
    void testAddSmsToQueue(FacebookSms facebookSms);

    void addSmsToQueue(SubmitSm submitSm);
}
