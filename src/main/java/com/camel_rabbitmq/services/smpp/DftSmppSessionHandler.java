package com.camel_rabbitmq.services.smpp;

import com.camel_rabbitmq.services.transit.TransitSms;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionHandler;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.*;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ref.WeakReference;

public class DftSmppSessionHandler implements SmppSessionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DftSmppSessionHandler.class);

    private final WeakReference<SmppSession> sessionRef;

    public DftSmppSessionHandler(SmppServerSession smppServerSession) {
        this.sessionRef = new WeakReference<>(smppServerSession);
    }

    @Override
    public void fireChannelUnexpectedlyClosed() {

    }

    @Override
    public PduResponse firePduRequestReceived(PduRequest pduRequest) {
        PduResponse response = null;
        TransitSms transitSms = new TransitSms();
        try {
            response = pduRequest.createResponse();
            SubmitSm mo = (SubmitSm) pduRequest;
            transitSms.addSmsToQueue(mo);
            int length = mo.getShortMessageLength();
            Address source_address = mo.getSourceAddress();
            Address dest_address = mo.getDestAddress();
            byte[] shortMessage = mo.getShortMessage();
            String sms= new String(shortMessage);
            logger.info(source_address + ", " + dest_address + ", " + sms);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public void firePduRequestExpired(PduRequest pduRequest) {

    }

    @Override
    public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) {

    }

    @Override
    public void fireUnexpectedPduResponseReceived(PduResponse pduResponse) {

    }

    @Override
    public void fireUnrecoverablePduException(UnrecoverablePduException e) {

    }

    @Override
    public void fireRecoverablePduException(RecoverablePduException e) {

    }

    @Override
    public void fireUnknownThrowable(Throwable t) {

    }

    @Override
    public String lookupResultMessage(int commandStatus) {
        return null;
    }

    @Override
    public String lookupTlvTagName(short tag) {
        return null;
    }
}
