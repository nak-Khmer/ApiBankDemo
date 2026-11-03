package com.khmerbank.start_api.dto;
import jakarta.validation.constraints.NotBlank;
public record CardStatusUpdateRequest(
    @NotBlank (message = "Card ID is required")
    String status
) {
    
}
