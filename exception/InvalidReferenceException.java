package com.cop_3060.exception;

/**
 * Thrown when a request references a related entity ID
 * that does not exist (e.g., invalid locationId or categoryId).
 */
public class InvalidReferenceException extends RuntimeException {
    public InvalidReferenceException(String message) {
        super(message);
    }
}
