package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.CardDTO;
import com.yourlife.your.life.model.entity.finance.Card;
import com.yourlife.your.life.model.vo.finance.card.CardPutVO;
import com.yourlife.your.life.model.vo.finance.card.CardPostVO;
import com.yourlife.your.life.service.finance.CardService;
import com.yourlife.your.life.utils.UserContext;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/service/api/v1")
public class CardController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CardService cardService;

    @Autowired
    private UserContext userContext;

    @PostMapping(value = "/cards",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CardDTO> save(@RequestBody @Valid CardPostVO cardPostVO){

        if(cardPostVO.getName() == null || cardPostVO.getDueDate() == null || cardPostVO.getModel() == null){
            throw new RuntimeException(ExceptionMessages.INVALID_REQUEST_COMPONENT);
        }

        Card card = modelMapper.map(cardPostVO,Card.class);

        card.setUser(userContext.returnUserCorrespondingToTheRequest());
        card.setCreatedAt(LocalDateTime.now());
        card.setDeleted(false);

        Card cardSave = cardService.save(card);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(cardSave,CardDTO.class));
    }

    @GetMapping(value = "/cards",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CardDTO>> getAll (){

        List<Card> cards = cardService.getAll(userContext.returnUserCorrespondingToTheRequest().getId());

        List<CardDTO> cardDTOS = new ArrayList<>();

        cards.forEach(card -> {
            cardDTOS.add(modelMapper.map(card,CardDTO.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(cardDTOS);
    }

    @GetMapping(value = "/cards/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDTO> getById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(cardService.getById(id),CardDTO.class));
    }

    @PutMapping(value = "/cards/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CardDTO> updated(@RequestBody @Valid CardPutVO cardPutVO,
                                           @PathVariable String id){

        Card cardRequest = modelMapper.map(cardPutVO,Card.class);

        Card card = cardService.getById(id);

        card.setName(cardRequest.getName() != null ? cardRequest.getName() : card.getName());
        card.setDueDate(cardRequest.getDueDate() != null ? cardRequest.getDueDate() : card.getDueDate());
        card.setModel(cardPutVO.getModel() != null ? cardRequest.getModel() : card.getModel());

        Card cardSave = cardService.save(card);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(cardSave,CardDTO.class));
    }


    @PatchMapping(value = "/cards/{id}/deleted",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleted(@PathVariable String id){

        Card card = cardService.getById(id);

        card.setDeleted(true);
        card.setUpdatedAt(LocalDateTime.now());
        card.setDeletedAt(LocalDateTime.now());

        cardService.save(card);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
