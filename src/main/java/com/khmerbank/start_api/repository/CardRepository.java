package com.khmerbank.start_api.repository;

import com.khmerbank.start_api.model.Card;
// import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository 
public interface CardRepository extends JpaRepository<Card, Long> {

    // ១. Derived Query: ស្វែងរកតាមប្រភេទកាត (VISA, MASTERCARD)
    List<Card> findByCardType(String cardType);

    // ២. Derived Query: ស្វែងរកឈ្មោះម្ចាស់កាតដោយមិនប្រកាន់អក្សរតូចធំ (Case-Insensitive Search)
    List<Card> findByCardHolderNameContainingIgnoreCase(String keyword);

    // ៣. JPQL Query: ស្វែងរកកាតដែលមាន Status ACTIVE តាមប្រភេទជាក់លាក់
    @Query("SELECT c FROM Card c WHERE c.cardType = :cardType AND c.status = 'ACTIVE'")
    List<Card> findActiveCardsByType(@Param("cardType") String cardType);

    // ៤. Derived Query: ឆែកមើលថាតើមានឈ្មោះម្ចាស់កាតនេះក្នុងប្រព័ន្ធឬនៅ (Return boolean)
    boolean existsByCardHolderName(String cardHolderName);
}