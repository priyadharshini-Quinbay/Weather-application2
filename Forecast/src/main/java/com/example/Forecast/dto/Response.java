package com.example.Forecast.dto;

public class Response<T> {
    private T data;

    public Response(T data, Integer errorCode, String errorMessage) {
        this.data = data;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private boolean result;
    private Integer errorCode;
    private String errorMessage;
    public Response(T data) {
        this.data = data;
        result = true;
    }

    public Response(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        result = false;
    }

    public T getData() {
        return data;
    }

    public boolean isResult() {
        return result;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}