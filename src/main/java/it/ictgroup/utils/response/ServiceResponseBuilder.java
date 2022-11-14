package it.ictgroup.utils.response;

import it.ictgroup.utils.LogServiceMessage;
import it.ictgroup.utils.LogServiceMessageBuilder;

import java.util.List;
import java.util.Map;

public class ServiceResponseBuilder<T> {

    private ServiceResponse<T> response;

    public ServiceResponseBuilder() {
        response = new ServiceResponse<>();
    }

    public static <T> ServiceResponseBuilder<T> builder() {
        return new ServiceResponseBuilder();
    }

    public ServiceResponseBuilder result(T result) {
        this.response.setResult(result);
        return this;
    }

    public ServiceResponseBuilder meta(Map<String, Object> meta) {
        this.response.setMeta(meta);
        return this;
    }

    public ServiceResponseBuilder meta(String key, Object value) {
        this.response.addMeta(key, value);
        return this;
    }

    public ServiceResponseBuilder message(LogServiceMessage msg) {
        this.response.addMessage(msg);
        return this;
    }

    public ServiceResponseBuilder info(String code, String message, Object... params) {
        this.response.addMessage(LogServiceMessageBuilder.info(code, message, params));
        return this;
    }

    public ServiceResponseBuilder success(String code, String message, Object... params) {
        this.response.addMessage(LogServiceMessageBuilder.success(code, message, params));
        return this;
    }

    public ServiceResponseBuilder warning(String code, String message, Object... params) {
        this.response.addMessage(LogServiceMessageBuilder.warning(code, message, params));
        return this;
    }

    public ServiceResponseBuilder error(String code, String message, Object... params) {
        this.response.addMessage(LogServiceMessageBuilder.error(code, message, params));
        return this;
    }

    public ServiceResponseBuilder severe(String code, String message, Object... params) {
        return this.severe(code, message, null, params);
    }

    public ServiceResponseBuilder severe(String code, String message, Throwable throwable, Object... params) {
        this.response.addMessage(LogServiceMessageBuilder.severe(code, message, throwable, params));
        return this;
    }

    public ServiceResponseBuilder messages(List<LogServiceMessage> msgs) {
        this.response.addMessages(msgs);
        return this;
    }

    public boolean hasErrors() {
        return this.response.hasErrors();
    }

    public ServiceResponse from(Map<String, Object> serviceResponseAsMap) {
        result((T) serviceResponseAsMap.get("result"));
        messages((List<LogServiceMessage>) serviceResponseAsMap.get("messages"));
        return build();
    }

    public ServiceResponse build() {
        return this.response;
    }
}
