package com.camel_rabbitmq.services.smpp;

import com.camel_rabbitmq.CamelRabbitmqApplication;
import com.cloudhopper.smpp.SmppServerConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppServer;
import com.cloudhopper.smpp.type.SmppChannelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InitialSmsc {

    private static final Logger logger = LoggerFactory.getLogger(InitialSmsc.class);

    @EventListener(ApplicationReadyEvent.class)
    public void initialSmsc() {
        try {
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

            DefaultSmppServer smppServer = new DefaultSmppServer(configuration, new DftSmppServerHandler(), executor, monitorExecutor);

            logger.info("Starting SMPP server...");
            smppServer.start();
            logger.info("SMPP server started");

            logger.info("Server counters: {}", smppServer.getCounters());
        } catch (SmppChannelException e) {
            e.printStackTrace();
        }
    }
}
