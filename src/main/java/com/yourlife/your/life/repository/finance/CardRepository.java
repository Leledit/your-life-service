package com.yourlife.your.life.repository.finance;

import com.yourlife.your.life.model.entity.finance.Card;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface CardRepository extends MongoRepository<Card,String> {

    Optional<ArrayList<Card>> findAllByUser_IdAndDeleted(String id,Boolean deleted);
}
