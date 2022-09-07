package com.mastercard.example.demo.exceptions;

public class ConsumerAlreadyHasThisAddressForThisTypeException extends RuntimeException {
    public ConsumerAlreadyHasThisAddressForThisTypeException(Integer id) {
        super("Could not find consumer " + id);
    }

}
