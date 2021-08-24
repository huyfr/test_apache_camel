package com.camel_rabbitmq.services.smpp;

import com.camel_rabbitmq.services.transit.ITransitSms;
import com.cloudhopper.smpp.SmppServerHandler;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.pdu.BaseBind;
import com.cloudhopper.smpp.pdu.BaseBindResp;
import com.cloudhopper.smpp.type.SmppProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DftSmppServerHandler implements SmppServerHandler {

    private static final Logger logger = LoggerFactory.getLogger(DftSmppServerHandler.class);

    private final ITransitSms transitSms;

    @Autowired
    public DftSmppServerHandler(ITransitSms transitSms) {
        this.transitSms = transitSms;
    }

    @Override
    public void sessionBindRequested(Long sessionId, SmppSessionConfiguration sessionConfiguration, BaseBind bindRequest) throws SmppProcessingException {
        sessionConfiguration.setName("Application.SMPP." + sessionConfiguration.getSystemId());
    }

    @Override
    public void sessionCreated(Long sessionId, SmppServerSession session, BaseBindResp preparedBindResponse) throws SmppProcessingException {
        logger.info("Session created: {}", session);
        DftSmppSessionHandler sessionHandler = new DftSmppSessionHandler(transitSms);
        sessionHandler.setSessionRef(session);
        session.serverReady(sessionHandler);
    }

    @Override
    public void sessionDestroyed(Long sessionId, SmppServerSession session) {
        logger.info("Session destroyed: {}", session);
        if (session.hasCounters()) {
            logger.info(" final session rx-submitSM: {}", session.getCounters().getRxSubmitSM());
        }
        session.destroy();
    }
}
