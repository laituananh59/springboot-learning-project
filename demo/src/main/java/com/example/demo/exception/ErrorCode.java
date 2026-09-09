package com.example.demo.exception;

import com.sun.net.httpserver.HttpsConfigurator;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User already exists", HttpStatus.CONFLICT),
    USERNAME_EXISTED(1002, "Username already exists", HttpStatus.CONFLICT),
    EMAIL_EXISTED(1003, "Email already exists", HttpStatus.CONFLICT),
    INVALID_REQUEST(1004, "Invalid request", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus){
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
