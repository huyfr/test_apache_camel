package cloudHopper;

import cloudHopper.services.SmppServerHandler;
import com.cloudhopper.smpp.SmppServerConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppServer;
import com.cloudhopper.smpp.type.SmppChannelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class CloudHopperApplication {

    private static final Logger logger = LoggerFactory.getLogger(CloudHopperApplication.class);

    public static void main(String[] args) throws SmppChannelException, IOException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        ScheduledThreadPoolExecutor monitorExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1, new ThreadFactory() {
            private AtomicInteger sequence = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("SmppServerSessionWindowMonitorPool-" + sequence.getAndIncrement());
                return t;
            }
        });

        SmppServerConfiguration configuration = new SmppServerConfiguration();
        configuration.setPort(2776);
        configuration.setMaxConnectionSize(10);
        configuration.setNonBlockingSocketsEnabled(true);
        configuration.setDefaultRequestExpiryTimeout(30000);
        configuration.setDefaultWindowMonitorInterval(15000);
        configuration.setDefaultWindowSize(5);
        configuration.setDefaultWindowWaitTimeout(configuration.getDefaultRequestExpiryTimeout());
        configuration.setDefaultSessionCountersEnabled(true);
        configuration.setJmxEnabled(true);

        DefaultSmppServer smppServer = new DefaultSmppServer(configuration, new SmppServerHandler(), executor, monitorExecutor);

        logger.info("Starting SMPP server...");
        smppServer.start();
        logger.info("SMPP server started");

        logger.info("Server counters: {}", smppServer.getCounters());
    }
}
