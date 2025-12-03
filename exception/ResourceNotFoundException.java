package com.cameron.cop3060.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception for handling resource not found scenarios
 * Returns HTTP 404 status when thrown
 * 
 * @author Cameron Brown
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    // ==========================================
    // Constructors
    // ==========================================

    /**
     * Simple constructor with message only
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with resource details
     * Creates message like: "User not found with id : 1"
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * Constructor with message and cause
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // ==========================================
    // Getters
    // ==========================================

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
