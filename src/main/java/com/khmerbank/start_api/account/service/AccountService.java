package com.khmerbank.start_api.account.service;

import org.springframework.stereotype.Service;

import com.khmerbank.start_api.account.model.Account;
import com.khmerbank.start_api.account.repository.AccountRepository;
import com.khmerbank.start_api.dto.CardResponse;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.khmerbank.start_api.account.dto.AccountResponse;

@Service 
public class AccountService {
    private final AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));
        List<CardResponse> cardResponses = account.getCards().stream()
                        .map(card -> new CardResponse(
                        card.getId(),
                        card.getCardHolderName(),
                        card.getCardType(),
                        card.getStatus(),
                        account.getId()
                ))
                .toList();

        return new AccountResponse(
            account.getId(),
            account.getAccountNumber(),
            account.getBalance(),
            account.getAccountType(),
            cardResponses
        );
    }
}
