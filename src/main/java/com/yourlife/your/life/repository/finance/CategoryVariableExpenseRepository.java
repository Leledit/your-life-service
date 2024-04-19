package com.yourlife.your.life.repository.finance;

import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryVariableExpenseRepository extends MongoRepository<CategoryVariableExpense,String> {
}
