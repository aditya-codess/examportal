package com.exam.exception;

/**
 * Thrown when a new or updated exam schedule overlaps with an existing
 * non-cancelled schedule's time window.
 */
public class SchedulingConflictException extends RuntimeException {
    public SchedulingConflictException(String message) {
        super(message);
    }
}
