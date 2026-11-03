package com.khmerbank.start_api.account.dto;

import java.math.BigDecimal;

public record TransferResponse(
    String message,
    BigDecimal transferredAmount,
    BigDecimal remainingBalance
) {
    
}
