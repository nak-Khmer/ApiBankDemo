package com.khmerbank.start_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// ប្រាប់ Spring ថា រាល់ពេល Exception នេះត្រូវបាន throw ត្រូវឆ្លើយតប HTTP 404 Not Found
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
