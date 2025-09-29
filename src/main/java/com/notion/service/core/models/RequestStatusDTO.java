package com.notion.service.core.models;

public class RequestStatusDTO<T> {
    private RequestStatusEntries status;
    private String message;
    private T data;

    public RequestStatusDTO(RequestStatusEntries status, T data) {
        this.status = status;
        this.message = status.getMessage();
        this.data = data;
    }

    public RequestStatusEntries getStatus() {
        return status;
    }

    public void setStatus(RequestStatusEntries status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
