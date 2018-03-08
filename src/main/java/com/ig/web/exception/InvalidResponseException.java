package com.ig.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.rmi.RemoteException;

@ResponseStatus(
        code = HttpStatus.BAD_GATEWAY
)
public class InvalidResponseException extends RemoteException {

    public InvalidResponseException(String message) {
        this(message, (Throwable) null);
    }

    public InvalidResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
