package com.khmerbank.start_api.account.service;

import org.springframework.stereotype.Service;

import com.khmerbank.start_api.account.model.Account;
import com.khmerbank.start_api.account.repository.AccountRepository;
import com.khmerbank.start_api.account.repository.TransactionRepository;
import com.khmerbank.start_api.dto.CardResponse;
import com.khmerbank.start_api.exception.InsufficientBalanceException;
import com.khmerbank.start_api.exception.ResourceNotFoundException;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.khmerbank.start_api.account.dto.AccountResponse;
import com.khmerbank.start_api.account.dto.TransactionResponse;
import com.khmerbank.start_api.account.dto.TransferRequest;
import com.khmerbank.start_api.account.dto.TransferResponse;
import com.khmerbank.start_api.account.model.Transaction;

@Service 
public class AccountService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository , TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
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

    @Transactional 
    public TransferResponse transferMoney(TransferRequest request) {

        //cannot transfer money to the same account
        if(request.sourceAccountId().equals(request.targetAccountId())) {
            throw new IllegalArgumentException("Cannot transfer money to the same account");
        }
        //find source account
        Account sourceAccount = accountRepository.findById(request.sourceAccountId())
                            .orElseThrow(()-> new ResourceNotFoundException("Source account not found with id : "+request.sourceAccountId()));
        Account targetAccount = accountRepository.findById(request.targetAccountId())
                            .orElseThrow(()-> new ResourceNotFoundException("Target account not found with id : "+request.targetAccountId()));
        //check source account balance 
        if(sourceAccount.getBalance().compareTo(request.amount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance. Current balance is: " + sourceAccount.getBalance());
        }
        //cut money from source account 
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.amount()));
        //add money to target account 
        targetAccount.setBalance(targetAccount.getBalance().add(request.amount()));
        // កត់ត្រាចូលក្នុង Audit Log (Table: transactions)
        Transaction transaction = new Transaction(
                sourceAccount.getId(),
                targetAccount.getId(),
                request.amount(),
                "TRANSFER",
                "SUCCESS"
        );
    transactionRepository.save(transaction);
        return new TransferResponse(
            "Transfer complete successufully",
            request.amount(),
            sourceAccount.getBalance()
        );
        }   
    @Transactional (readOnly = true)
    public List<TransactionResponse> getAccountTransactions(Long accountId){
        if(!accountRepository.existsById(accountId)){
            throw new ResourceNotFoundException("Account not with ID : "+ accountId);
        }
        List<Transaction> transaction = transactionRepository
        .findBySourceAccountIdOrTargetAccountIdOrderByCreatedAtDesc(accountId, accountId);
        return transaction.stream()
                .map(t -> new TransactionResponse(
                    t.getId(),
                    t.getSourceAccountId(),
                    t.getTargetAccountId(),
                    t.getAmount(),
                    t.getStatus(),
                    t.getCreatedAt()
                ))
                .toList();
    }
}
