package com.ecare.network;

import com.ecare.plans.DataStorage;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;


@MessageDriven(name = "EcareMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "activemq/queue/ecareClients"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})

public class Listener implements MessageListener {

    private static Logger logger = LogManager.getLogger(Listener.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Setter private Queue queue;

    @Inject
    private DataStorage storage;

    @Override
    public void onMessage(Message message) {
        try {
            MsgConverter converter = new MsgConverter();
            DataChangeNotification msg = (DataChangeNotification) converter.fromMessage(message);
            storage.loadPlans();
            logger.trace("Received notification message from: " + msg.getSender());
        } catch (Exception e) {
            logger.error("Failed to parse message: " + e.toString());
            e.printStackTrace();
        }
    }
}
