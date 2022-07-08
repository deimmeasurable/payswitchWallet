package com.example.paytwitch.exception;

import jdk.jshell.spi.ExecutionControl;

public class WalletPasswordWrongException extends UserNotFoundException {
    public WalletPasswordWrongException(String message) {
        super(message);
    }
}
