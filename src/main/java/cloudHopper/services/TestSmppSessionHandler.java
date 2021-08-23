package cloudHopper.services;

import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.*;
import com.cloudhopper.smpp.type.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

public class TestSmppSessionHandler extends DefaultSmppSessionHandler {

    private static final Logger logger = LoggerFactory.getLogger(TestSmppSessionHandler.class);

    private WeakReference<SmppSession> sessionRef;

    public TestSmppSessionHandler(SmppServerSession session) {
        this.sessionRef = new WeakReference<SmppSession>(session);
    }

    @Override
    public PduResponse firePduRequestReceived(PduRequest pduRequest) {
        PduResponse response = null;
        try {
            response = pduRequest.createResponse();
            logger.info("SMS Received: {}", pduRequest);
            SubmitSm mo = (SubmitSm) pduRequest;
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
}
