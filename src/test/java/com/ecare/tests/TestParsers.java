package com.ecare.tests;

import com.ecare.network.DataChangeNotification;
import com.ecare.network.MsgConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.assertj.core.api.Assertions.assertThat;

import com.mockrunner.mock.jms.MockSession;
import com.mockrunner.mock.jms.MockQueueConnection;
import com.mockrunner.jms.DestinationManager;
import com.mockrunner.jms.ConfigurationManager;

import javax.jms.Message;
import javax.jms.Session;

public class TestParsers {

    @Test
    public void testParseNotification() throws Exception {

        final String sender = "Test";

        DestinationManager destManager = new DestinationManager();
        ConfigurationManager confManager = new ConfigurationManager();
        MockQueueConnection connection = new MockQueueConnection(destManager, confManager);
        MockSession session =(MockSession)connection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);

        MsgConverter converter = new MsgConverter();

        DataChangeNotification msg = new DataChangeNotification();
        msg.setKey(msg.getKeyDefault());
        msg.setSender(sender);

        Message msgSerialized = converter.toMessage(msg, session);
        DataChangeNotification parsed = (DataChangeNotification) converter.fromMessage(msgSerialized);

        assertEquals(parsed, msg);
    }
}
