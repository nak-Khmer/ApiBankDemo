package com.khmerbank.start_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CardRequest(
    @NotBlank (message = "Card holder name is required")
    @Size (min = 3, max = 50, message = "Card holder name must be between 3 and 50 characters")
    String cardHolderName,
    @NotBlank (message = "Card type is required")
    String cardType,
    @NotNull(message = "Account ID is required")
    Long accountId
)
{
    
}
