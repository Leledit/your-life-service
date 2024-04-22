package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.CardDTO;
import com.yourlife.your.life.model.entity.finance.Card;

import java.util.List;

public interface CardService {
    Card save(Card card);
    List<Card> getAll(String userId);
    Card getById(String id);
}
