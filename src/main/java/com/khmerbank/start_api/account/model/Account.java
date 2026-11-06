package com.khmerbank.start_api.account.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.khmerbank.start_api.model.Card;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table (name = "accounts")
public class Account {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column (name = "account_number" ,nullable = false,unique = true,length = 20)
    private String accountNumber;
    @Column  (name = "balance",nullable = false)
    private BigDecimal balance;
    @Column (name = "account_type",nullable = false,length = 20)
    private String accountType; //SAVINGS, PAYROLL , CURRENT

    // ទំនាក់ទំនង One-to-Many: គណនីមួយមានកាតច្រើន
    // mappedBy = "account" សំដៅលើឈ្មោះ Field "account" នៅក្នុង Class Card

    @OneToMany (mappedBy = "account",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();
    public Account(){}

    public Account(String accountNumber, BigDecimal balance, String accountType) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
    public List<Card> getCards() { return cards; }
    public void setCards(List<Card> cards) { this.cards = cards;   }

    public static  void main(String[] args){
        System.out.println();

    }
}
