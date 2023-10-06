package com.generic.core.base.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode( callSuper = false )
class ApiValidationError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError( String object, String message ) {
        this.object = object;
        this.message = message;
    }

}