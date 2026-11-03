package com.khmerbank.start_api.account.dto;

import java.util.List;
import java.math.BigDecimal;
import com.khmerbank.start_api.dto.CardResponse;

    public record AccountResponse(
        Long accountId,
        String accountNumber,
        BigDecimal balance,
        String accountType,
        List<CardResponse> cards
    ) {}
