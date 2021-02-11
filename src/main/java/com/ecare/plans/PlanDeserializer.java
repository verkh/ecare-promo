package com.ecare.plans;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom deserializer for Plan that are received from main server via REST API
 */
public class PlanDeserializer extends JsonDeserializer<Plan> {

    /**
     * Deserializes plan from JSON
     * @param jsonParser - parser
     * @param deserializationContext - context
     * @return deserialized Plan from JSON node
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public Plan deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Plan plan = new Plan();
        plan.setId(node.get("id").asLong());
        plan.setName(node.get("name").asText());
        plan.setPrice(node.get("price").asDouble());

        JsonNode optionsNode = node.get("options");
        if(optionsNode.isArray()) {
            List<Plan.Option> options = new ArrayList<Plan.Option>();
            for(final JsonNode oNode : optionsNode) {
                boolean deprecated = oNode.get("deprecated").asBoolean();
                if(deprecated) continue;

                Plan.Option option = new Plan.Option();
                option.setId(oNode.get("id").asLong());
                option.setName(oNode.get("name").asText());
                option.setDescription(oNode.get("description").asText());
                option.setPrice(oNode.get("price").asDouble());
                option.setTurnOnPrice(oNode.get("turnOnPrice").asDouble());

                options.add(option);
            }
            plan.setOptions(options);
        }
        return plan;
    }
}
