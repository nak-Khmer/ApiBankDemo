package com.khmerbank.start_api.exception;

import java.time.LocalDateTime;

public record ErrorResponse (
    int statusCode,
    String message,
    String path,
    LocalDateTime timestamp
){
    
}
