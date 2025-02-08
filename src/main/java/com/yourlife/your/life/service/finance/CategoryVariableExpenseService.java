package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePostDTO;
import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePutDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;

import java.util.ArrayList;
import java.util.List;

public interface CategoryVariableExpenseService {

    CategoryVariableExpense save(CategoryVariableExpensePostDTO categoryVariableExpensePostDTO);
    List<CategoryVariableExpense> getAll();
    CategoryVariableExpense getById(String id);
    CategoryVariableExpense update(String id, CategoryVariableExpensePutDTO categoryVariableExpensePutDTO);
    void delete(String id);
    /*Exit saveExit(String idCategory, ExitPostDTO exitPostDTO);
    Exit updateExit(String id, ExitPutDTO exitPutDTO);*/
}
