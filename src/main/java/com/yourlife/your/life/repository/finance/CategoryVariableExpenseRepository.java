package com.yourlife.your.life.repository.finance;

import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.ArrayList;
import java.util.Optional;

public interface CategoryVariableExpenseRepository extends MongoRepository<CategoryVariableExpense,String> {

    Optional<ArrayList<CategoryVariableExpense>> findAllByUser_IdAndDeleted(String id,Boolean deleted);

    Optional<CategoryVariableExpense> findByIdAndDeleted(String id,Boolean deleted);
}
