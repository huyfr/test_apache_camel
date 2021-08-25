package com.camel_rabbitmq.services.smpp;

import com.camel_rabbitmq.services.transit.ITransitSms;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ref.WeakReference;

@Service
public class DftSmppSessionHandler extends DefaultSmppSessionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DftSmppSessionHandler.class);
    private WeakReference<SmppSession> sessionRef;
    private final ITransitSms transitSms;
    private final long startTime = System.currentTimeMillis();

    @Autowired
    public DftSmppSessionHandler(ITransitSms transitSms) {
        this.transitSms = transitSms;
    }

    public void setSessionRef(SmppServerSession smppServerSession) {
        this.sessionRef = new WeakReference<>(smppServerSession);
    }

    @Override
    public PduResponse firePduRequestReceived(PduRequest pduRequest) {
        PduResponse response = null;
        logger.info("Start time: {}", startTime);
        try {
            SmppSession session = sessionRef.get();
            SubmitSm mo = (SubmitSm) pduRequest;
            transitSms.addSmsToQueue(mo);
            response = pduRequest.createResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
