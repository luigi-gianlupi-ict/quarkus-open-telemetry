package it.coopservice.asset.utils;

import co.elastic.clients.elasticsearch.core.search.Hit;

import java.util.HashMap;
import java.util.Map;

import static it.coopservice.asset.management.AppConstants.*;

public class ElasticUtils {

    public static final String WITH = "_";

    public static Map<String, Object> buildDocument(Hit<?> hit) {
        return buildDocument(hit.id(), JsonDataUtils.jsonDataToMap(hit.source()));
    }

    @SuppressWarnings("unchecked,rawtypes")
    public static Map<String, Object> buildDocument(String id, Map<String, Object> sourceAsMap) {
        sourceAsMap.put(VERSION, id);
        sourceAsMap.computeIfPresent(MODEL, (k, v) -> {
            if (v instanceof Map) {
                ((Map) v).computeIfPresent(CODE, (ck, code) -> code.toString());
            }
            return v;
        });
        return sourceAsMap;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toReference(Map<String, Object> map) {
        Map<String, Object> reference = new HashMap<>();
        Map<String, Object> model = (Map<String, Object>) map.get(MODEL);
        reference.put(TYPE, map.get(SCHEMA_CODE));
        reference.put(CODE, model.get(CODE));
        reference.put(DESCRIPTION, model.get(DESCRIPTION));
        reference.put(UUID, map.get(UUID));
        return reference;
    }

    /*
     * Data una mappa restituisce il field annidato richiesto. Es:
     * source = {
     *   dataIn: 2020-11-23,
     *   model: {
     *     type: "edificio"
     *   }
     * }
     *
     * getNestedField(source, "model.type") == "edificio"
     */
    @SuppressWarnings("unchecked")
    public static Object getNestedField(Map<String, Object> source, String field) {
        String[] splittedField = field.split("\\.");
        Object value = source;
        for (String key : splittedField) {
            if(value instanceof Map) {
                value = ((Map<String, Object>) value).get(key);
            } else {
                return null;
            }
        }
        return value;
    }

}
