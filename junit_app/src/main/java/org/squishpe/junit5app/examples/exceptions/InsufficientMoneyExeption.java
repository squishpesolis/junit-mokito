package org.squishpe.junit5app.examples.exceptions;

public class InsufficientMoneyExeption extends RuntimeException{

    public InsufficientMoneyExeption(String message) {
        super(message);
    }
}
