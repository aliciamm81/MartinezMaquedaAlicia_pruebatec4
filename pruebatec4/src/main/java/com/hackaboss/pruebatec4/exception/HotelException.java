package com.hackaboss.pruebatec4.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HotelException extends Exception {
    public HotelException(String message) {
        super(message);
    }

}
