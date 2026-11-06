package com.khmerbank.start_api.account.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import com.khmerbank.start_api.account.service.AccountService;
import com.khmerbank.start_api.account.dto.AccountResponse;
import com.khmerbank.start_api.account.dto.TransactionResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.khmerbank.start_api.account.dto.TransferRequest;
import com.khmerbank.start_api.account.dto.TransferResponse;
import java.util.List;
@RestController 
@RequestMapping("api/v1/accounts")
public class AccountController {
    private AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping ("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable("id") Long id) {
        return  ResponseEntity.ok(accountService.getAccountById(id));
    }
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transferMoney(@RequestBody TransferRequest request) {
        return ResponseEntity.ok(accountService.transferMoney(request));
    }
    @GetMapping("/{id}/transactions")
    public  ResponseEntity<List<TransactionResponse>> getAccountTransactions(@PathVariable ("id") Long id){
        List<TransactionResponse> transactions = accountService.getAccountTransactions(id); 
        return ResponseEntity.ok(transactions);
    }
}
