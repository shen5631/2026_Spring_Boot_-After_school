package com.mirim.board1.dto;

import java.util.Map;

public class ApiErrorResponse {
    private final int status;
    private final String message;
    private final Map<String,String> error;

    public ApiErrorResponse(int status, String message, Map<String, String> error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getError() {
        return error;
    }
}
