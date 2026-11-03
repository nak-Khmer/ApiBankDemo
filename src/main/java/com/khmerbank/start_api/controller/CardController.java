    package com.khmerbank.start_api.controller;
    import org.springframework.data.domain.Page;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.ResponseStatus;

    import com.khmerbank.start_api.service.CardService;
    import java.util.List;
    import jakarta.validation.Valid;
    import com.khmerbank.start_api.dto.*;

    @RestController 
    @RequestMapping ("/api/v1/cards")
    public class CardController {
        private final CardService cardService;

        public CardController(CardService cardService) {
            this.cardService = cardService;
        }

        @PostMapping
        public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CardRequest request) {
            CardResponse response = cardService.createCard(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        @GetMapping("/{id}")
        public ResponseEntity<CardResponse> getCard(@PathVariable  Long id) {
            CardResponse response = cardService.getCard(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/search")
        public ResponseEntity<List<CardResponse>> searchCard(
            @RequestParam(name  = "keyword", required = false) String keyword,
            @RequestParam(name = "cardType", required = false) String cardType
        ){
            List<CardResponse> response = cardService.searchCard(keyword, cardType);
            return ResponseEntity.ok(response);
        }

        @PutMapping ("/{id}/status")
        public ResponseEntity<CardResponse> updateCardStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody CardStatusUpdateRequest request) {
            CardResponse updatedCard = cardService.updateCardStatus(id, request);
            return ResponseEntity.ok(updatedCard);
        }
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)// DELETE: លុបកាតចេញពីប្រព័ន្ធ (ប្រើ HTTP 204 No Content ពេលជោគជ័យ)
        public ResponseEntity<Void> deleteCard(@PathVariable("id") Long id) {
            cardService.deleteCard(id);
            return ResponseEntity.noContent().build();
        }
        @GetMapping 
        public ResponseEntity<Page<CardResponse>> getAllCards(
            @RequestParam (name = "page", defaultValue = "0") int page,
            @RequestParam (name = "size", defaultValue = "10") int size,
            @RequestParam (name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam (name = "direction", defaultValue = "asc") String direction
        ){
            Page<CardResponse> cards = cardService.getAllCardsWithPagination(page, size, sortBy, direction);
            return ResponseEntity.ok(cards);
        }
}
