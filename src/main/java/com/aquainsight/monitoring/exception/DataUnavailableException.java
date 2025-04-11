package com.aquainsight.monitoring.exception;

// Custom exception for handling specific cases
public class DataUnavailableException extends RuntimeException {
    public DataUnavailableException(String message) {
        super(message);
    }
}
