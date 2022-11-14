package it.ictgroup.utils;


import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class LogServiceMessageDecorator {

    public static List<LogServiceMessage> decorate(List<LogServiceMessage> messages, Object... params) {
        return messages.stream().map(msg -> decorate(msg, params)).collect(Collectors.toList());
    }

    public static LogServiceMessage decorate(LogServiceMessage msg, Object... params) {
        return new LogServiceMessage(msg.getCode(), msg.getStatus(), decorate(msg.getMessage(), params));
    }

    public static String decorate(String msg, Object... params) {
        StringBuilder newMsg = new StringBuilder(msg);
        if(params != null && params.length > 0) {
            newMsg.append(": [");
            String sep = "";
            for (Object p : params) {
                newMsg.append(sep).append(p);
                sep = ", ";
            }
            newMsg.append("]");
        }
        return newMsg.toString();
    }

}
