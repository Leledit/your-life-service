package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePostDTO;
import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePutDTO;
import com.yourlife.your.life.model.dto.finance.exit.ExitPostDTO;
import com.yourlife.your.life.model.dto.finance.exit.ExitPutDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.model.entity.finance.Exit;

import java.util.ArrayList;

public interface VariableExpensesCategoryService {

    CategoryVariableExpense save(CategoryVariableExpensePostDTO categoryVariableExpensePostDTO);
    ArrayList<CategoryVariableExpense> getAll(String userId);
    CategoryVariableExpense getById(String id);
    CategoryVariableExpense update(String id, CategoryVariableExpensePutDTO categoryVariableExpensePutDTO);
    void delete(String id);
    /*Exit saveExit(String idCategory, ExitPostDTO exitPostDTO);
    Exit updateExit(String id, ExitPutDTO exitPutDTO);*/
}
