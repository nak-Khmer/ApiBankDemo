package com.khmerbank.start_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khmerbank.start_api.account.model.Account;
import com.khmerbank.start_api.account.repository.AccountRepository;
import com.khmerbank.start_api.dto.CardRequest;
import com.khmerbank.start_api.dto.CardResponse;
import com.khmerbank.start_api.dto.CardStatusUpdateRequest;
import com.khmerbank.start_api.exception.ResourceNotFoundException;
import com.khmerbank.start_api.model.Card;
import com.khmerbank.start_api.repository.CardRepository;

import java.util.List;

@Service 
public class CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository; // ១. បន្ថែម AccountRepository

    // ២. Inject ទាំង CardRepository និង AccountRepository តាម Constructor
    public CardService(CardRepository cardRepository, AccountRepository accountRepository) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public CardResponse createCard(CardRequest request) {
        // ៣. ស្វែងរក Account តាម accountId ពី Database ជាមុនសិន (បើគ្មាន នឹងបោះ 404 Not Found)
        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + request.accountId()));

        // ៤. បង្កើត Entity Card ដោយភ្ជាប់ Object account ទៅជាមួយ
        Card card = new Card(
            request.cardHolderName(),
            request.cardType(),
            "ACTIVE",
            account // បញ្ជូន account ចូល Constructor ទី ៤ នៃ Card.java
        );

        // ៥. រក្សាទុកចូលក្នុង PostgreSQL
        Card savedCard = cardRepository.save(card);

        // ៦. savedCard មាន ID និង Foreign Key account_id រួចរាល់
        return new CardResponse(
            savedCard.getId(),
            savedCard.getCardHolderName(),
            savedCard.getCardType(),
            savedCard.getStatus(),
            savedCard.getAccount() != null ? savedCard.getAccount().getId() : null
        );
    }

    public CardResponse getCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found with ID: " + id));

        return new CardResponse(
            card.getId(),
            card.getCardHolderName(),
            card.getCardType(),
            card.getStatus(),
            card.getAccount() != null ? card.getAccount().getId() : null
        );
    }

    public List<CardResponse> searchCard(String keyword, String cardType) {
        List<Card> cards;
        if (keyword != null && !keyword.isBlank()) {
            cards = cardRepository.findByCardHolderNameContainingIgnoreCase(keyword);
        } else if (cardType != null && !cardType.isBlank()) {
            cards = cardRepository.findActiveCardsByType(cardType.toUpperCase());
        } else {
            cards = cardRepository.findAll();
        }

        return cards.stream()
                .map(card -> new CardResponse(
                        card.getId(),
                        card.getCardHolderName(),
                        card.getCardType(),
                        card.getStatus(),
                        card.getAccount() != null ? card.getAccount().getId() : null
                ))
                .toList();
    }

    @Transactional
    public CardResponse updateCardStatus(Long id, CardStatusUpdateRequest request) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found with ID: " + id));

        card.setStatus(request.status().toUpperCase());

        return new CardResponse(
                card.getId(),
                card.getCardHolderName(),
                card.getCardType(),
                card.getStatus(),
                card.getAccount() != null ? card.getAccount().getId() : null
        );
    }

    @Transactional 
    public void deleteCard(Long id) {
        if (!cardRepository.existsById(id)) {
            throw new ResourceNotFoundException("Card not found with ID: " + id);
        }
        cardRepository.deleteById(id);
    }
    public Page<CardResponse> getAllCardsWithPagination(int page,int size , String sortBy, String direction) {
        if(size > 50){
            size = 50;
        }
        Sort sort = direction.equalsIgnoreCase("desc")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page ,size , sort) ;

        Page<Card> cardPage = cardRepository.findAll(pageable);

        return cardPage.map(card -> new CardResponse(
            card.getId(),
            card.getCardHolderName(),
            card.getCardType(),
            card.getStatus(),
            card.getAccount() != null ? card.getAccount().getId() : null
        ));
    }
}