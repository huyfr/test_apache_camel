package cloudHopper.services;

import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.pdu.BaseBind;
import com.cloudhopper.smpp.pdu.BaseBindResp;
import com.cloudhopper.smpp.type.SmppProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmppServerHandler implements com.cloudhopper.smpp.SmppServerHandler {

    private static final Logger logger = LoggerFactory.getLogger(SmppServerHandler.class);

    @Override
    public void sessionBindRequested(Long sessionId, SmppSessionConfiguration sessionConfiguration, BaseBind bindRequest) throws SmppProcessingException {
        sessionConfiguration.setName("Application.SMPP." + sessionConfiguration.getSystemId());
    }

    @Override
    public void sessionCreated(Long sessionId, SmppServerSession session, BaseBindResp preparedBindResponse) throws SmppProcessingException {
        logger.info("Session created: {}", session);
        session.serverReady(new TestSmppSessionHandler(session));
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
