package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.model.entity.finance.Card;
import com.yourlife.your.life.repository.finance.CardRepository;
import com.yourlife.your.life.service.finance.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public List<Card> getAll(String userId) {
        return cardRepository.findAllByUser_IdAndDeleted(userId,false).orElse(null);

    }

    @Override
    public Card getById(String id) {

        Card card = cardRepository.findById(id).orElse(null);

        if(card == null || card.getDeleted()){
            throw new RuntimeException("Nenhuma registro foi encontrado!");
        }

        return card;
    }
}
