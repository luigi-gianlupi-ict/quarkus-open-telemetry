package it.ictgroup.utils.json;


import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.json.JsonValue;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class JacksonMapper {

    public static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());


    public static String mapAsJSON(Map<String,Object> map) {
        String mapAsJson;
        try {
            mapAsJson = MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return mapAsJson;
    }

    public static String objAsJSON(Object obj) {
        String objAsJSON;
        try {
            objAsJSON = MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return objAsJSON;
    }

    public static Map<String, Object> stringToMap(String string) {
        try {
            return MAPPER.readValue(string, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Map<String, Object>> stringToListOfMaps(String string) {
        try {
            return MAPPER.readValue(string, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    public static Object stringToObject(String string) {
        try {
            return MAPPER.readValue(string, new TypeReference<>() {
            });

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JacksonMapper() {
    }

    public static Map<String,Object> jsonDataToMap(Object data) {
        if (data instanceof Map) {
            return (Map<String, Object>) data;
        } else {
            JsonValue json = ((JsonData) data).toJson();
            return stringToMap(json.toString());
        }
    }
}
