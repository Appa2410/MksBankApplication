package com.bank.Application.Exception;

public class EmailAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyExistsException(String exception) {
        super(exception);
    }
}