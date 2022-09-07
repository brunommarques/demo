package com.mastercard.example.demo.exceptions;

public class ConsumerNotFoundException extends RuntimeException {
    public ConsumerNotFoundException(Integer id) {
        super("Could not find consumer " + id);
    }

}
