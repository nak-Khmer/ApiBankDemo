package com.khmerbank.start_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

import com.khmerbank.start_api.account.model.Account;

import jakarta.persistence.Column;

@Entity 
@Table (name = "cards")
public class Card {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column (name = "card_holder_name",nullable = false,length = 100)
    private String cardHolderName;
    @Column (name = "card_type",nullable = false,length = 20)
    private String cardType;
    @Column (name = "status",nullable = false,length = 20)
    private String status;

    // ទំនាក់ទំនង Many-to-One: កាតច្រើនជាកម្មសិទ្ធិរបស់គណនីតែមួយ
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false) // បង្កើត Foreign Key column: account_id
    private Account account;
    
    // 1. No-Args Constructor (កាតព្វកិច្ចសម្រាប់ JPA/Hibernate)
    public Card(){}

    public Card(Long id, String cardHolderName, String cardType, String status) {
        Id = id;
        this.cardHolderName = cardHolderName;
        this.cardType = cardType;
        this.status = status;
    }

    public Card(String cardHolderName, String cardType, String status) {
        this.cardHolderName = cardHolderName;
        this.cardType = cardType;
        this.status = status;
    }

    public Long getId() {
        return Id;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCardType() {
        return cardType;
    }

    public Card(String cardHolderName, String cardType, String status, Account account) {
        this.cardHolderName = cardHolderName;
        this.cardType = cardType;
        this.status = status;
        this.account = account;
    }

    public String getStatus() {
        return status;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    
}
