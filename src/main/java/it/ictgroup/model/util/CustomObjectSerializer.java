package it.ictgroup.model.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.ictgroup.model.pojo.CustomObject;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CustomObjectSerializer implements Serializer<CustomObject> {

    static Logger LOGGER = LoggerFactory.getLogger(CustomObjectSerializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, CustomObject data) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(data).getBytes();
        } catch (Exception exception) {
            LOGGER.error("Error in serializing object {} ", data);
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}
