package com.ecommerce.project.exceptions.custom;

public class ResourceNotFoundException extends RuntimeException {

    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceNotFoundException(String field, String fieldName, String resourceName) {
        super(String.format("%s not found with %s : %s", resourceName, field,fieldName));
        this.field = field;
        this.fieldName = fieldName;
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(Long fieldId, String fieldName, String resourceName) {
        super(String.format("%s not found with %s : %d", resourceName, fieldName ,fieldId));
        this.fieldId = fieldId;
        this.fieldName = fieldName;
        this.resourceName = resourceName;
    }
}
