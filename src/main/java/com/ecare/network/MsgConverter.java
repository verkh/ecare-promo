package com.ecare.network;

import com.ecare.network.DataChangeNotification;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;

/**
 * Converter is responsible for serializing and deserializing messages
 */
public class MsgConverter implements MessageConverter {

    /**
     * Converts to network message
     * @param object target message
     * @param session current session
     * @return network message
     * @throws JMSException
     * @throws MessageConversionException
     */
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        DataChangeNotification notification = (DataChangeNotification) object;
        MapMessage message = session.createMapMessage();
        message.setString("key", notification.getKey());
        message.setString("sender", notification.getSender());
        return message;
    }

    /**
     * Deserializing message from network message
     * @param message source message
     * @return parsed object
     * @throws JMSException
     * @throws MessageConversionException
     */
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        MapMessage mapMessage = (MapMessage) message;
        return new DataChangeNotification(mapMessage.getString("key"), mapMessage.getString("sender"));
    }
}
