package com.exam.exception;

/**
 * Thrown when a requested resource (exam, schedule, result, student, etc.) does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
