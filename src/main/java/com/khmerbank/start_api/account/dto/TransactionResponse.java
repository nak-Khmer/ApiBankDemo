package com.khmerbank.start_api.account.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public record TransactionResponse(
    Long id,
    Long sourceAccountId,
    Long targetAccountId,
    BigDecimal amount,
    String status,
    LocalDateTime createdAt
) {
}

