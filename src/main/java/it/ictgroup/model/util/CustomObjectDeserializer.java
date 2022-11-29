package it.ictgroup.model.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.ictgroup.model.pojo.CustomObject;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CustomObjectDeserializer implements Deserializer<CustomObject> {
    static Logger LOGGER = LoggerFactory.getLogger(CustomObjectDeserializer.class);


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public CustomObject deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        CustomObject object = null;
        try {
            object = mapper.readValue(data, CustomObject.class);
        } catch (Exception exception) {
            LOGGER.error("Error in serializing bytes {} ", exception);
        }
        return object;
    }

    @Override
    public void close() {
    }

}