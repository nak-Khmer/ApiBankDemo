package com.khmerbank.start_api.account.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransferRequest(
    @NotNull (message="Source Account ID is required")
    Long sourceAccountId,
    @NotNull (message="Target Account ID is required")
    Long targetAccountId,
    @NotNull (message="Amount is required")
    @Positive(message = "Transfer amount must be greater than zero")
    BigDecimal amount
) {
    
}
