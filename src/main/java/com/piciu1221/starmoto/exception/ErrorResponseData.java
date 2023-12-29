package com.piciu1221.starmoto.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Collections;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponseData {
    private final String type;
    private final String message;
    private final Map<String, String> details;

    public ErrorResponseData(String type, String message, Map<String, String> details) {
        this.type = type;
        this.message = message;
        this.details = details;
    }

    // Constructor without details
    public ErrorResponseData(String type, String message) {
        this.type = type;
        this.message = message;
        this.details = Collections.emptyMap();
    }
}
