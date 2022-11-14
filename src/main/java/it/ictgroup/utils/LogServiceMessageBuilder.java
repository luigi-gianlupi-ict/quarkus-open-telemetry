package it.ictgroup.utils;


import java.util.Map;

@SuppressWarnings("unused")
public class LogServiceMessageBuilder {
    public static LogServiceMessage info(String code, String message, Object... params) {
        return build(code, LogServiceMessage.Status.INFO, message, params);
    }

    public static LogServiceMessage success(String code, String message, Object... params) {
        return build(code, LogServiceMessage.Status.SUCCESS, message, params);
    }

    public static LogServiceMessage warning(String code, String message, Object... params) {
        return build(code, LogServiceMessage.Status.WARNING, message, params);
    }

    public static LogServiceMessage warning(String code, String message, Map<String,?> payload, Object... params) {
        return build(code, LogServiceMessage.Status.WARNING, message, payload, params);
    }

    public static LogServiceMessage error(String code, String message, Object... params) {
        return build(code, LogServiceMessage.Status.ERROR, message, params);
    }

    public static LogServiceMessage severe(String code, String message, Object... params) {
        return build(code, LogServiceMessage.Status.SEVERE, message, params);
    }

    public static LogServiceMessage severe(String code, Throwable throwable, Object... params) {
        return severe(code, throwable.getMessage(), throwable, params);
    }

    public static LogServiceMessage severe(String code, String message, Throwable throwable, Object... params) {
        LogServiceMessage msg = build(code, LogServiceMessage.Status.SEVERE, message, params);
        msg.setThrowable(throwable);
        return msg;
    }

    public static LogServiceMessage build(String code, LogServiceMessage.Status status, String message, Object... params) {
        LogServiceMessage msg = new LogServiceMessage(code, status, message);
        return LogServiceMessageDecorator.decorate(msg, params);
    }

    public static LogServiceMessage build(String code, LogServiceMessage.Status status, String message,
                                          Map<String,?> payload, Object... params) {
        LogServiceMessage msg = new LogServiceMessage(code, status, message);
        msg = LogServiceMessageDecorator.decorate(msg, params);
        msg.setPayload(payload);
        return msg;
    }
}

