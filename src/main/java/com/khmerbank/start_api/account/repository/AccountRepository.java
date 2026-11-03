package com.khmerbank.start_api.account.repository;

import java.util.Optional;

// import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.khmerbank.start_api.account.model.Account;

// @Repository 
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    
}
