package com.server.api.management.exception;

import lombok.Data;

@Data
public class ValidationException extends RuntimeException {

    public ValidationException(String msg) {
        super(msg);
    }
}
