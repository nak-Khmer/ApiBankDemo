package com.khmerbank.start_api.account.dto;
import java.math.BigDecimal;
public record TransactionRequest(
    Long sourceAccountId,
    Long targetAccountId,
    BigDecimal amount
) {
    
}
