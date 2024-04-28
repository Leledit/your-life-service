package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.CardDTO;
import com.yourlife.your.life.model.entity.finance.Card;
import com.yourlife.your.life.model.vo.finance.CardChangingVO;
import com.yourlife.your.life.model.vo.finance.CardRegisterVO;
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
    public ResponseEntity<CardDTO> register (@RequestBody @Valid CardRegisterVO cardRegisterVO){

        Card card = modelMapper.map(cardRegisterVO,Card.class);

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

    @PutMapping(value = "/cards",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CardDTO> updated(@RequestBody @Valid CardChangingVO cardChangingVO){

        Card cardRequest = modelMapper.map(cardChangingVO,Card.class);

        Card card = cardService.getById(cardRequest.getId());

        card.setName(cardRequest.getName() != null ? cardRequest.getName() : card.getName());
        card.setDueDate(cardRequest.getDueDate() != null ? cardRequest.getDueDate() : card.getDueDate());
        card.setModel(cardChangingVO.getModel() != null ? cardRequest.getModel() : card.getModel());

        cardService.save(card);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(card,CardDTO.class));
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
