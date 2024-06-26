package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.CategoryVariableExpenseDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import java.util.ArrayList;
import java.util.List;

public interface CategoryVariableExpenseService {

    CategoryVariableExpense save(CategoryVariableExpense categoryVariableExpense);
    ArrayList<CategoryVariableExpense> getAll(String userId);
    CategoryVariableExpense getById(String id);
    List<CategoryVariableExpense> createdSeveral(List<CategoryVariableExpense> categoryVariableExpenses);
}
