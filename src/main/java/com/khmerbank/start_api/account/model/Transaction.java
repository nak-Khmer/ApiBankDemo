package com.khmerbank.start_api.account.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity 
@Table (name = "transaction")
public class Transaction {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "source_account_id", nullable = false)
    private Long sourceAccountId;
    @Column (name = "target_account_id",nullable = false)
    private Long targetAccountId;
    @Column (name = "amount",nullable = false,precision = 38,scale = 2)
    private BigDecimal amount;
    @Column (name = "transaction_type",nullable = false,length = 20)
    private String transactionType;
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Transaction(Long sourceAccountId, Long targetAccountId, BigDecimal amount, String transactoinType,
            String status) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.transactionType = transactoinType;
        this.status = status;
    }
    public Transaction() {}
    public Transaction(Long id, Long sourceAccountId, Long targetAccountId, BigDecimal amount, String transactionType,
            String status) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.status = status;
    }
    @PrePersist 
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
    public Long getId() {
        return id;
    }
    public Long getSourceAccountId() {
        return sourceAccountId;
    }
    public Long getTargetAccountId() {
        return targetAccountId;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public String getTransactoinType() {
        return transactionType;
    }
    public String getStatus() {
        return status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    

    
}
