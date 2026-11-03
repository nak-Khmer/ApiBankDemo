package com.khmerbank.start_api.dto;

public record CardResponse(
    Long cardId,
    String cardHolderName,
    String cardType,
    String cardStatus,
    Long accountId
) {}
