package com.hackaboss.pruebatec4.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VueloException extends Exception {
    public VueloException(String message) {
        super(message);
    }
}
