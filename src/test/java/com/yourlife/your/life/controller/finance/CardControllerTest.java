package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.CardDTO;
import com.yourlife.your.life.model.entity.finance.Card;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.vo.finance.card.CardPostVO;
import com.yourlife.your.life.model.vo.finance.card.CardPutVO;
import com.yourlife.your.life.service.finance.CardService;
import com.yourlife.your.life.utils.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Card")
class CardControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CardService cardService;

    @Mock
    private UserContext userContext;

    @InjectMocks
    private CardController cardController;

    private User userMock;

    private Card cardMock;

    private CardDTO cardDTOMock;
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

        cardDTOMock = new CardDTO();
        cardDTOMock.setId("6625739518b9de67f587a657");
        cardDTOMock.setName("Cartão do Nubank");
        cardDTOMock.setDueDate("15/07/2028");
        cardDTOMock.setModel("Nubank");
    }

    @Test
    @DisplayName("save - Creating new record successfully!")
    void testSave() {
        CardPostVO cardPostVOMock = new CardPostVO();
        cardPostVOMock.setName("Cartão do Nubank");
        cardPostVOMock.setDueDate("15/07/2028");
        cardPostVOMock.setModel("Nubank");

        when(modelMapper.map(any(CardPostVO.class),eq(Card.class))).thenReturn(cardMock);
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(cardService.save(cardMock)).thenReturn(cardMock);
        when(modelMapper.map(any(Card.class),eq(CardDTO.class))).thenReturn(cardDTOMock);

        ResponseEntity<CardDTO> cardDTOResponseEntity = cardController.save(cardPostVOMock);

        assertEquals(HttpStatus.OK, cardDTOResponseEntity.getStatusCode());
        assertEquals(cardDTOMock,cardDTOResponseEntity.getBody());
    }

    @Test
    @DisplayName("save - Causing an exception when creating a record with invalid data!")
    void testSave_InvalidInput() {
        CardPostVO cardPostVOMock = new CardPostVO();

        assertThrows(RuntimeException.class, () -> cardController.save(cardPostVOMock), ExceptionMessages.INVALID_REQUEST_COMPONENT);
    }

    @Test
    @DisplayName("getAll - Searching for all cards that are registered in the system")
    void testGetAll() {
        List<Card> cardsMock = new ArrayList<>();
        cardsMock.add(new Card());

        List<CardDTO> cardDTOS = new ArrayList<>();
        cardDTOS.add(new CardDTO());

        CardDTO cardDTOMock = new CardDTO();

        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(cardService.getAll(userMock.getId())).thenReturn(cardsMock);
        when(modelMapper.map(any(Card.class),eq(CardDTO.class))).thenReturn(cardDTOMock);

        ResponseEntity<List<CardDTO>> listResponseEntity = cardController.getAll();

        assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
        assertEquals(cardDTOS,listResponseEntity.getBody());

    }

    @Test
    @DisplayName("getById - Searching for a single record")
    void testGetById_Returning_Card() {
        when(cardService.getById(cardMock.getId())).thenReturn(cardMock);
        when(modelMapper.map(any(Card.class),eq(CardDTO.class))).thenReturn(cardDTOMock);

        ResponseEntity<CardDTO> cardDTOResponseEntity = cardController.getById(cardMock.getId());

        assertEquals(HttpStatus.OK, cardDTOResponseEntity.getStatusCode());
        assertEquals(cardDTOMock,cardDTOResponseEntity.getBody());
    }


    @Test
    @DisplayName("updated - Updating a record")
    void testUpdated() {
        CardPutVO cardPutVOMock = new CardPutVO();
        cardPutVOMock.setName("Cartão do Nubank 2");
        cardPutVOMock.setDueDate("20/07/2028");
        cardPutVOMock.setModel("Nubank 2");

        when(modelMapper.map(any(CardPutVO.class),eq(Card.class))).thenReturn(cardMock);
        when(cardService.getById(cardMock.getId())).thenReturn(cardMock);
        when(cardService.save(cardMock)).thenReturn(cardMock);
        when(modelMapper.map(any(Card.class),eq(CardDTO.class))).thenReturn(cardDTOMock);

        ResponseEntity<CardDTO> cardDTOResponseEntity = cardController.updated(cardPutVOMock,cardMock.getId());

        assertEquals(HttpStatus.OK, cardDTOResponseEntity.getStatusCode());
        assertEquals(cardDTOMock,cardDTOResponseEntity.getBody());
    }

    @Test
    @DisplayName("deleted - Deleting a record")
    void testDeleted() {
        when(cardService.getById(cardMock.getId())).thenReturn(cardMock);
        when(cardService.save(cardMock)).thenReturn(cardMock);

        ResponseEntity<Void> cardDTOResponseEntity = cardController.deleted(cardMock.getId());

        assertEquals(HttpStatus.OK, cardDTOResponseEntity.getStatusCode());
    }
}