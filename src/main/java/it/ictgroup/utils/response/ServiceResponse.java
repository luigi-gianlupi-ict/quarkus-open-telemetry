package it.ictgroup.utils.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.ictgroup.utils.LogServiceMessage;
import it.ictgroup.utils.LogServiceMessageBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class ServiceResponse<T> {
    private List<LogServiceMessage> messages;
    private T result;
    private Map<String, Object> meta;

    public ServiceResponse() {
        this(null, null);
    }

    public ServiceResponse(T result) {
        this(result, null);
    }

    public ServiceResponse(T result, List<LogServiceMessage> messages) {
        this.result = result;
        this.messages = messages;
    }

    public List<LogServiceMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<LogServiceMessage> messages) {
        this.messages = messages;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    public void addMeta(String key, Object value) {
        if(getMeta() == null) {
            setMeta(new HashMap<>());
        }
        getMeta().put(key, value);
    }

    public void addMessage(LogServiceMessage message) {
        if(this.messages == null) {
            this.messages = new ArrayList<>();
        }
        if(message != null) {
            this.messages.add(message);
        }
    }

    public void addMessages(List<LogServiceMessage> messages) {
        if(this.messages == null) {
            this.messages = new ArrayList<>();
        }
        if(messages != null && messages.size() > 0) {
            this.messages.addAll(messages);
        }
    }

    public void addInfoMessage(String code, String message, Object...  params) {
        addMessage(LogServiceMessageBuilder.info(code, message, params));
    }

    public void addWarnMessage(String code, String message, Object...  params) {
        addMessage(LogServiceMessageBuilder.warning(code, message, params));
    }

    public void addErrorMessage(String code, String message, Object...  params) {
        addMessage(LogServiceMessageBuilder.error(code, message, params));
    }

    public void addSevereMessage(String code, String message, Object...  params) {
        addMessage(LogServiceMessageBuilder.severe(code, message, params));
    }

    @JsonProperty("hasErrors")
    public boolean hasErrors() {
        if(messages != null && messages.size() > 0) {
            for(LogServiceMessage msg: messages) {
                if(msg.isError()) {
                    return true;
                }
            }
        }
        return false;
    }

    @JsonProperty("hasWarnings")
    public boolean hasWarnings() {
        if(messages != null && messages.size() > 0) {
            for(LogServiceMessage msg: messages) {
                if(msg.isWarning()) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getStatus() {
        if(hasErrors()) {
            return "errors";
        }
        if(hasWarnings()) {
            return "warnings";
        }
        return "ok";
    }

    @Deprecated
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("meta", getMeta());
        map.put("result", getResult());
        map.put("messages", getMessages());
        map.put("status", getStatus());
        map.put("hasErrors", hasErrors());
        map.put("hasWarnings", hasWarnings());
        return map;
    }

    /*public static ServiceResponseBuilder builder() {
        return new ServiceResponseBuilder();
    }

    public static class ServiceResponseBuilder {
        public static ServiceResponse fromServiceResponseMap(Map<String, Object> map) {
            ServiceResponse serviceResponse = new ServiceResponse();
            serviceResponse.setMessages((List<LogServiceMessage>) map.get("messages"));
            serviceResponse.setResult(map.get("result"));
            serviceResponse.setMeta((Map<String, Object>) map.get("meta"));
            return serviceResponse;
        }
    }*/
}
