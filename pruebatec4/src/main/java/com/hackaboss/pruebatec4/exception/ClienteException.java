package com.hackaboss.pruebatec4.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor

public class ClienteException extends Exception {
    public ClienteException(String message) {
        super(message);
    }
}
