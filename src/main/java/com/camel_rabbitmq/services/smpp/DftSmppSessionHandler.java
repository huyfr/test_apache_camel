package com.camel_rabbitmq.services.smpp;

import com.camel_rabbitmq.services.transit.TransitSms;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.*;
import com.cloudhopper.smpp.type.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ref.WeakReference;

@Service
public class DftSmppSessionHandler extends DefaultSmppSessionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DftSmppSessionHandler.class);

    @Autowired
    private TransitSms transitSms;

    private WeakReference<SmppSession> sessionRef;

    public void setSessionRef(SmppServerSession smppServerSession) {
        this.sessionRef = new WeakReference<>(smppServerSession);
    }

    @Override
    public PduResponse firePduRequestReceived(PduRequest pduRequest) {
        PduResponse response = null;
        try {
            SmppSession session = sessionRef.get();
            SubmitSm mo = (SubmitSm) pduRequest;
            transitSms.addSmsToQueue(mo);
            Address source_address = mo.getSourceAddress();
            Address dest_address = mo.getDestAddress();
            byte[] shortMessage = mo.getShortMessage();
            String sms= new String(shortMessage);
            logger.info(source_address + ", " + dest_address + ", " + sms);
            response = pduRequest.createResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
