package com.ecare.tests;

import com.ecare.network.DataChangeNotification;
import com.ecare.network.MsgConverter;
import com.ecare.plans.Plan;
import com.ecare.plans.PlanDeserializer;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mockrunner.mock.jms.MockSession;
import com.mockrunner.mock.jms.MockQueueConnection;
import com.mockrunner.jms.DestinationManager;
import com.mockrunner.jms.ConfigurationManager;

import javax.jms.Message;
import javax.jms.Session;
import java.io.StringReader;

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

        assertEquals(msg, parsed);
    }
    
    @Test
    public void testJsonParser() throws Exception {
        
        String data = "{ \"id\":1, \"name\":\"Dictator\", \"price\":1000.0, \"options\":[ { \"attachedId\":1, \"id\":1, \"name\":\"Block everyone\", \"price\":10.0, \"description\":\"Whenever you want you can block the Internet in the whole country. Make them suffer\", \"turnOnPrice\":1.0, \"undisablable\":true, \"deprecated\":false, \"enabled\":false, \"restrictions\":[ { \"id\":1, \"optionId1\":1, \"optionId2\":5, \"type\":\"INCOMPATIBLE\" } ] }, { \"attachedId\":2, \"id\":2, \"name\":\"Internet\", \"price\":0.0, \"description\":\"Use the magic to communicate with other people vie chats and send the catpics\", \"turnOnPrice\":1.0, \"undisablable\":false, \"deprecated\":false, \"enabled\":false, \"restrictions\":[ { \"id\":2, \"optionId1\":2, \"optionId2\":5, \"type\":\"INCOMPATIBLE\" } ] }, { \"attachedId\":3, \"id\":3, \"name\":\"Calls\", \"price\":0.0, \"description\":\"Just take your phone and click call!\", \"turnOnPrice\":1.0, \"undisablable\":false, \"deprecated\":false, \"enabled\":false, \"restrictions\":[ { \"id\":3, \"optionId1\":3, \"optionId2\":5, \"type\":\"INCOMPATIBLE\" } ] }, { \"attachedId\":4, \"id\":4, \"name\":\"Spam-suffer\", \"price\":0.0, \"description\":\"Use this opportunity to make your peasants suffer!\", \"turnOnPrice\":1.0, \"undisablable\":true, \"deprecated\":false, \"enabled\":false, \"restrictions\":[ { \"id\":4, \"optionId1\":4, \"optionId2\":5, \"type\":\"INCOMPATIBLE\" } ] }, { \"attachedId\":5, \"id\":6, \"name\":\"News\", \"price\":0.0, \"description\":\"Receive news everyday\", \"turnOnPrice\":1.0, \"undisablable\":false, \"deprecated\":false, \"enabled\":false, \"restrictions\":[ ] }, { \"attachedId\":6, \"id\":7, \"name\":\"Bieber\", \"price\":0.0, \"description\":\"Let them suffer listening Justin Bieber\", \"turnOnPrice\":1.0, \"undisablable\":false, \"deprecated\":false, \"enabled\":false, \"restrictions\":[ ] }, { \"attachedId\":7, \"id\":8, \"name\":\"You don't pay\", \"price\":0.0, \"description\":\"Ley your friend pay for your call\", \"turnOnPrice\":1.0, \"undisablable\":false, \"deprecated\":false, \"enabled\":false, \"restrictions\":[ ] } ] }";
        StringReader reader = new StringReader(data);
        JsonParser jParser = new JsonFactory().createParser(data);
        ObjectMapper mapper = new ObjectMapper();
        jParser.setCodec(mapper);
        PlanDeserializer parser = new PlanDeserializer();
        Plan plan = parser.deserialize(jParser, null);

        assertEquals("Dictator", plan.getName());
        assertEquals((Double)1000.0, plan.getPrice());
        assertEquals((Long)1l, plan.getId());
        assertEquals(7, plan.getOptions().size());
    }
    
}
