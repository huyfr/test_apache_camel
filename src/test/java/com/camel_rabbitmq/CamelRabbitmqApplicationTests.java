package com.camel_rabbitmq;

import com.camel_rabbitmq.services.transit.impl.TransitSms;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.type.SmppInvalidArgumentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static com.camel_rabbitmq.ulti.CamelSmppValueHeader.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CamelRabbitmqApplicationTests {

    @Autowired
    private TransitSms transitSms;

    @Test
    void contextLoads() {
    }

    @Test
    public void testNewCachedThreadPool() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });

        assertEquals(3, executor.getPoolSize());
        assertEquals(0, executor.getQueue().size());
    }

    @Test
    public void testAddMessagesToQueue() throws SmppInvalidArgumentException {
            SubmitSm mo = new SubmitSm();
            Address destAdd = new Address();
            destAdd.setAddress("123456789");
            destAdd.setNpi(SMPP_NPI_UNK);
            destAdd.setTon(SMPP_TON_ALNUM);
            mo.setDestAddress(destAdd);
            Address sourceAdd = new Address();
            sourceAdd.setAddress("987654321");
            sourceAdd.setNpi(SMPP_NPI_ISDN);
            sourceAdd.setTon(SMPP_TON_INTL);
            mo.setSourceAddress(sourceAdd);
            String shortMessage = "Hello wolrd";
            byte[] byteShortMessage = shortMessage.getBytes();
            mo.setShortMessage(byteShortMessage);

            for (int i = 0; i < 10000; i++) {
                transitSms.addSmsToQueue(mo);
            }
    }
}
