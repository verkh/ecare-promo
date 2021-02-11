package com.ecare.network;

import com.ecare.plans.DataStorage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
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

    @Autowired
    private JmsTemplate jmsTemplate;

    @Setter private Queue queue;

    @Inject
    private DataStorage storage;

    @Override
    public void onMessage(Message message) {
        storage.loadPlans();
        System.out.println("Received: " + message.toString());
    }
}
