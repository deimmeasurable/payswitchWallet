package com.example.paytwitch.exception;

public class WalletNotFoundException extends UserNotFoundException {
    public WalletNotFoundException(String message) {
        super(message);
    }
}
