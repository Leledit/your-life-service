package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.entity.finance.Card;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.repository.finance.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Card")
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    private User userMock;

    private Card cardMock;

    @BeforeEach
    public void setUp(){
        userMock = new User();
        userMock.setId("6621b1c02c3dbe50ac7d6319");
        userMock.setEmail("test@teste.com.br");
        userMock.setName("leandro");
        userMock.setPassword("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS");

        cardMock = new Card();
        cardMock.setId("6625739518b9de67f587a657");
        cardMock.setName("Cartão do Nubank");
        cardMock.setDueDate("15/07/2028");
        cardMock.setModel("Nubank");
        cardMock.setDeleted(false);
        cardMock.setUser(userMock);
    }

    @Test
    @DisplayName("Card - Check success when registering a new card")
    void testSave() {
        when(cardRepository.save(cardMock)).thenReturn(cardMock);

        Card card = cardService.save(cardMock);

        assertNotNull(card);
    }

    @Test
    @DisplayName("Card - Search for all registered cards, returning null")
    void testGetAllReturning_Null() {
        when(cardRepository.findAllByUser_IdAndDeleted(userMock.getId(),false)).thenReturn(Optional.empty());

        List<Card> cards = cardService.getAll(userMock.getId());

        assertNull(cards);
    }

    @Test
    @DisplayName("Card - Search for all registered cards, returning a list array")
    void testGetAllReturning_List() {
        ArrayList<Card> mockListCards = new ArrayList<>();
        mockListCards.add(new Card());

        when(cardRepository.findAllByUser_IdAndDeleted(userMock.getId(),false)).thenReturn(Optional.of(mockListCards));

        List<Card> cards = cardService.getAll(userMock.getId());

        assertEquals(1, cards.size());
    }
    @Test
    @DisplayName("Card - Searching for a single card")
    void testGetByIdReturning_Card() {
        when(cardRepository.findById("6625739518b9de67f587a657")).thenReturn(Optional.of(cardMock));

        Card card = cardService.getById("6625739518b9de67f587a657");

        assertEquals("6625739518b9de67f587a657", card.getId());
    }

    @Test
    @DisplayName("Card - throwing an exception when trying to search for a deleted card")
    void testGetByIdReturning_Exception() {
        cardMock.setDeleted(true);

        when(cardRepository.findById("6625739518b9de67f587a657")).thenReturn(Optional.of(cardMock));

        assertThrows(RuntimeException.class, () -> cardService.getById("6625739518b9de67f587a657"), ExceptionMessages.NOT_FOUND);
    }
}