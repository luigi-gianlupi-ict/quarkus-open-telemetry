package it.ictgroup.utils;

import java.time.Instant;
import java.util.Map;

@SuppressWarnings("unused")
public class LogServiceMessage {
    public enum Status {INFO, SUCCESS, WARNING, ERROR, SEVERE}

    protected Instant instant;
    protected Status status;
    protected String code;
    protected String message;
    protected Map<String,?> payload;
    protected Throwable throwable;

    public LogServiceMessage(String code, Status status, String message) {
        this.instant = Instant.now();
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Map<String, ?> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, ?> payload) {
        this.payload = payload;
    }

    public boolean isSuccess() {
        return Status.SUCCESS.equals(getStatus());
    }

    public boolean isError() {
        return Status.ERROR.equals(getStatus()) || Status.SEVERE.equals(getStatus());
    }

    public boolean isWarning() {
        return Status.WARNING.equals(getStatus());
    }
}
