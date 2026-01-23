package com.ecommerce.project.exceptions.custom;

public class ResourceAlreadyPresentException extends RuntimeException {
    String resourceName;
    String field;
    String fieldValue;

    public ResourceAlreadyPresentException(String resourceName, String field, String fieldValue) {
        super(String.format("%s already found with %s : %s", resourceName, field, fieldValue));
        this.field = field;
        this.fieldValue = fieldValue;
        this.resourceName = resourceName;
    }
}
