package it.ictgroup.utils.exceptions;


import it.ictgroup.utils.LogServiceMessage;
import javax.transaction.Transactional;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Transactional
@SuppressWarnings("unused")
public class EmmaServiceException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final List<LogServiceMessage> messageList;
    private Object body;

    private static String composeMessage(List<LogServiceMessage> messageList) {
        if (messageList == null) {
            return null;
        }
        StringBuilder message = new StringBuilder();
        String sep = "";
        for (LogServiceMessage msg : messageList) {
            message.append(sep).append(msg.getStatus()).append(": ").append(msg.getMessage());
            sep = "; ";
        }
        return message.toString();
    }


    public EmmaServiceException(List<LogServiceMessage> messageList) {
        this(messageList, null);
    }

    public EmmaServiceException(List<LogServiceMessage> messageList, Object body) {
        super(composeMessage(messageList));
        this.messageList = messageList;
        this.body = body;
    }

    public EmmaServiceException(LogServiceMessage message) {
        this(message, null);
    }

    public EmmaServiceException(LogServiceMessage message, Object body) {
        this(Collections.singletonList(message), body);
    }

    public EmmaServiceException(String code, LogServiceMessage.Status status, String message) {
        this(code, status, message, null);
    }

    public EmmaServiceException(String code, LogServiceMessage.Status status, String message, Object body) {
        this(new LogServiceMessage(code, status, message), body);
    }

    public EmmaServiceException(String message) {
        this(message, null);
    }

    public EmmaServiceException(String message, Object body) {
        this(new LogServiceMessage(null, null, message), body);
    }

    public EmmaServiceException(Exception e) {
        this(e, null);
    }

    public EmmaServiceException(Exception e, Object body) {
        super(e.getMessage());
        this.messageList = new ArrayList<>();
        this.body = body;
    }

    public List<LogServiceMessage> getMessageList() {
        return messageList;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
