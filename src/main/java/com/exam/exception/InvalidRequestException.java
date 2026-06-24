package com.exam.exception;

/**
 * Thrown when a request fails business-rule validation
 * (e.g. invalid date ranges, malformed marks, duplicate submission).
 */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
